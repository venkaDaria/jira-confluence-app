import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CustomMetricValue from './custom-metric-value';
import CustomMetricValueDetail from './custom-metric-value-detail';

const Routes = ({ match }) => (
    <>
        <Switch>
            <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CustomMetricValueDetail}/>
            <ErrorBoundaryRoute path={match.url} component={CustomMetricValue}/>
        </Switch>
    </>
);

export default Routes;
