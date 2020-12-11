import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UpdatedIssuesMetricValue from './updated-issues-metric-value';
import UpdatedIssuesMetricValueDetail from './updated-issues-metric-value-detail';

const Routes = ({ match }) => (
    <>
        <Switch>
            <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UpdatedIssuesMetricValueDetail}/>
            <ErrorBoundaryRoute path={match.url} component={UpdatedIssuesMetricValue}/>
        </Switch>
    </>
);

export default Routes;
