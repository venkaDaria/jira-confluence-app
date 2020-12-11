const webpack = require('webpack');
const writeFilePlugin = require('write-file-webpack-plugin');
const BrowserSyncPlugin = require('browser-sync-webpack-plugin');
const FriendlyErrorsWebpackPlugin = require('friendly-errors-webpack-plugin');
const SimpleProgressWebpackPlugin = require('simple-progress-webpack-plugin');
const WebpackNotifierPlugin = require('webpack-notifier');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');
const sass = require('sass');
const path = require('path');

const utils = require('./utils.js');

const getTsLoaderRule = env => {
    const rules = [
        {
            loader: 'cache-loader',
            options: {
                cacheDirectory: path.resolve('build/cache-loader')
            }
        },
        {
            loader: 'thread-loader',
            options: {
                // there should be 1 cpu for the fork-ts-checker-webpack-plugin
                workers: require('os').cpus().length - 1
            }
        },
        {
            loader: 'ts-loader',
            options: {
                transpileOnly: true,
                happyPackMode: true
            }
        }
    ];
    if (env === 'development') {
        rules.unshift({
            loader: 'react-hot-loader/webpack'
        });
    }
    return rules;
};

module.exports = options => ({
    devtool: 'cheap-module-source-map', // https://reactjs.org/docs/cross-origin-errors.html
    mode: "development",
    entry: [
        'react-hot-loader/patch',
        './src/main/webapp/app/index'
    ],
    resolve: {
        extensions: [
            '.js', '.jsx', '.ts', '.tsx', '.json'
        ],
        modules: ['node_modules'],
        alias: {
            app: utils.root('src/main/webapp/app/')
        }
    },
    output: {
        path: utils.root('build/www'),
        filename: 'app/[name].bundle.js',
        chunkFilename: 'app/[id].chunk.js'
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: getTsLoaderRule(options.env),
                include: [utils.root('./src/main/webapp/app')],
                exclude: [utils.root('node_modules')]
            },
            {
                test: /\.(jpe?g|png|gif|svg|woff2?|ttf|eot)$/i,
                loader: 'file-loader',
                options: {
                    digest: 'hex',
                    hash: 'sha512',
                    name: 'content/[hash].[ext]'
                }
            },
            {
                enforce: 'pre',
                test: /\.jsx?$/,
                loader: 'source-map-loader'
            },
            {
                test: /\.tsx?$/,
                enforce: 'pre',
                loader: 'tslint-loader',
                exclude: [utils.root('node_modules')]
            },
            {
                test: /\.(sa|sc|c)ss$/,
                use: ['style-loader', 'css-loader', 'postcss-loader', {
                    loader: 'sass-loader',
                    options: { implementation: sass }
                }
                ]
            }
        ]
    },
    devServer: {
        stats: options.stats,
        hot: true,
        contentBase: './build/www',
        proxy: [{
            port: 9060,
            context: [
                '/api',
                '/swagger-ui.html',
                '/v2/api-docs'
            ],
            target: {
                host: "server-app",
                protocol: 'http:',
                port: 8080
            },
            changeOrigin: true,
            secure: false
        }],
        watchOptions: {
            ignored: /node_modules/
        }
    },
    stats: {
        children: false
    },
    optimization: {
        splitChunks: {
            cacheGroups: {
                commons: {
                    test: /[\\/]node_modules[\\/]/,
                    name: 'vendors',
                    chunks: 'all'
                }
            }
        }
    },
    plugins: [
        new webpack.DefinePlugin({
            'process.env': {
                NODE_ENV: `'${options.env}'`,
                VERSION: `'${utils.parseVersion()}'`,
                DEBUG_INFO_ENABLED: options.env === 'development',
                SERVER_API_URL: `''`
            }
        }),
        new ForkTsCheckerWebpackPlugin({ tslint: true }),
        new CopyWebpackPlugin([
            { from: './src/main/webapp/static/images', to: '/content' },
            { from: './src/main/webapp/favicon.ico', to: 'favicon.ico' }
        ]),
        new HtmlWebpackPlugin({
            template: './src/main/webapp/index.html',
            chunksSortMode: 'dependency',
            inject: 'body'
        }),
        new SimpleProgressWebpackPlugin({
                format: options.stats === 'minimal' ? 'compact' : 'expanded'
        }),
        new FriendlyErrorsWebpackPlugin(),
        new BrowserSyncPlugin({
            host: 'localhost',
            port: 3000,
            proxy: {
                target: 'http://localhost:9060'
            },
            socket: {
                clients: {
                    heartbeatTimeout: 60000
                }
            }
        }, {
            reload: false
        }),
        new webpack.HotModuleReplacementPlugin(),
        new writeFilePlugin(),
        new webpack.WatchIgnorePlugin([
            utils.root('src/test'),
        ]),
        new WebpackNotifierPlugin({
            title: 'Jira App'
        })
    ].filter(Boolean)
});