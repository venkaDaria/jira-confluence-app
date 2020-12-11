import './header.scss';

import React from 'react';

import { Collapse, Nav, Navbar, NavbarToggler } from 'reactstrap';
import LoadingBar from 'react-redux-loading-bar';

import { Brand, Home, Metrics } from './header-components';

export interface IHeaderState {
    menuOpen: boolean;
}

export default class Header extends React.Component<IHeaderState> {
    state: IHeaderState = {
        menuOpen: false
    };

    toggleMenu = () => {
        this.setState({ menuOpen: !this.state.menuOpen });
    };

    render() {
        return (
            <div id="app-header">
                <LoadingBar className="loading-bar"/>
                <Navbar dark expand="sm" fixed="top" className="jh-navbar">
                    <NavbarToggler aria-label="Menu" onClick={this.toggleMenu}/>
                    <Brand/>
                    <Collapse isOpen={this.state.menuOpen} navbar>
                        <Nav id="header-tabs" className="ml-auto" navbar>
                            <Home/>
                            <Metrics/>
                        </Nav>
                    </Collapse>
                </Navbar>
            </div>
        );
    }
}
