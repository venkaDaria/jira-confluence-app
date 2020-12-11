import React from 'react';

import { NavbarBrand, NavItem, NavLink } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import appConfig from 'app/config/constants';

export const Brand = props => (
    <NavbarBrand tag={Link} to="/" className="brand-logo">
        <span className="brand-title">Jira App </span>
        <span className="navbar-version">{appConfig.VERSION}</span>
    </NavbarBrand>
);

export const Home = props => (
    <NavItem>
        <NavLink tag={Link} to="/" className="d-flex align-items-center">
            <FontAwesomeIcon icon="home"/>
            <span>Home</span>
        </NavLink>
    </NavItem>
);

export const Metrics = props => (
    <NavItem>
        <NavLink tag={Link} to="/metrics" className="d-flex align-items-center">
            <FontAwesomeIcon icon="list"/>
            <span>Metrics</span>
        </NavLink>
    </NavItem>
);
