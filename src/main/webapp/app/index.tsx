import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { AppContainer } from 'react-hot-loader';

import initStore from './config/store';
import ErrorBoundary from './shared/error/error-boundary';
import AppComponent from './app';
import { loadIcons } from './config/icon-loader';

const store = initStore();

loadIcons();

const rootEl = document.getElementById('root');

const render = Component =>
    ReactDOM.render(
        <ErrorBoundary>
            <AppContainer>
                <Provider store={store}>
                    <div>
                        <Component/>
                    </div>
                </Provider>
            </AppContainer>
        </ErrorBoundary>,
        rootEl
    );

render(AppComponent);
