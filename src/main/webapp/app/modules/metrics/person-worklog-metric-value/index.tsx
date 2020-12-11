import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PersonWorklogMetricValue from './person-worklog-metric-value';
import PersonWorklogMetricValueDetail from './person-worklog-metric-value-detail';

const Routes = ({ match }) => (
    <>
        <Switch>
            <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PersonWorklogMetricValueDetail}/>
            <ErrorBoundaryRoute path={match.url} component={PersonWorklogMetricValue}/>
        </Switch>
    </>
);

export default Routes;
