import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import IssuesPerPersonMetricValue from './issues-per-person-metric-value';
import IssuesPerPersonMetricValueDetail from './issues-per-person-metric-value-detail';

const Routes = ({ match }) => (
    <>
        <Switch>
            <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IssuesPerPersonMetricValueDetail}/>
            <ErrorBoundaryRoute path={match.url} component={IssuesPerPersonMetricValue}/>
        </Switch>
    </>
);

export default Routes;
