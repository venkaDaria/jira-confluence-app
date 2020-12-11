import 'react-toastify/dist/ReactToastify.css';
import './app.scss';

import React from 'react';
import { connect } from 'react-redux';
import { Card } from 'reactstrap';
import { HashRouter as Router } from 'react-router-dom';
import { toast, ToastContainer, ToastPosition } from 'react-toastify';

import Header from 'app/shared/layout/header/header';
import Footer from 'app/shared/layout/footer/footer';
import ErrorBoundary from 'app/shared/error/error-boundary';
import AppRoutes from 'app/routes';

export class App extends React.Component<{}> {
    render() {
        const paddingTop = '60px';
        return (
            <Router>
                <div className="app-container" style={{ paddingTop }}>
                    <ToastContainer
                        position={toast.POSITION.TOP_LEFT as ToastPosition}
                        className="toastify-container"
                        toastClassName="toastify-toast"
                    />
                    <ErrorBoundary>
                        <Header menuOpen/>
                    </ErrorBoundary>
                    <div className="container-fluid view-container" id="app-view-container">
                        <Card className="jh-card">
                            <ErrorBoundary>
                                <AppRoutes/>
                            </ErrorBoundary>
                        </Card>
                        <Footer/>
                    </div>
                </div>
            </Router>
        );
    }
}

export default connect()(App);
