import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NotUpdatedIssuesMetricValue from './not-updated-issues-metric-value';
import NotUpdatedIssuesMetricValueDetail from './not-updated-issues-metric-value-detail';

const Routes = ({ match }) => (
    <>
        <Switch>
            <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NotUpdatedIssuesMetricValueDetail}/>
            <ErrorBoundaryRoute path={match.url} component={NotUpdatedIssuesMetricValue}/>
        </Switch>
    </>
);

export default Routes;
