import './home.scss';

import React from 'react';

import { connect } from 'react-redux';
import { Col, Row } from 'reactstrap';
import { Link } from 'react-router-dom';

export class Index extends React.Component<{}> {
    render() {
        return (
            <Row>
                <Col md="9">
                    <h2>Welcome!</h2>
                    <p className="lead">Go to <Link to={`/metrics`}>Metrics</Link></p>
                </Col>
                <Col md="3" className="pad">
                    <span className="hipster rounded"/>
                </Col>
            </Row>
        );
    }
}

export default connect()(Index);
