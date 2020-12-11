import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import NotUpdatedIssuesMetricValue from './not-updated-issues-metric-value';
import CustomMetricValue from './custom-metric-value';
import UpdatedIssuesMetricValue from './updated-issues-metric-value';
import IssuesPerPersonMetricValue from './issues-per-person-metric-value';
import PersonWorklogMetricValue from './person-worklog-metric-value';
import Metric from 'app/modules/metric';

const Routes = ({ match }) => (
    <div>
        <Switch>
            <ErrorBoundaryRoute exact path={match.url} component={Metric}/>
            <ErrorBoundaryRoute path={`${match.url}/NOT_UPDATED_ISSUES`} component={NotUpdatedIssuesMetricValue}/>
            <ErrorBoundaryRoute path={`${match.url}/UPDATED_ISSUES`} component={UpdatedIssuesMetricValue}/>
            <ErrorBoundaryRoute path={`${match.url}/ISSUES_PER_PERSON`} component={IssuesPerPersonMetricValue}/>
            <ErrorBoundaryRoute path={`${match.url}/PERSON_WORKLOG`} component={PersonWorklogMetricValue}/>
            <ErrorBoundaryRoute path={`${match.url}/:metricName`} component={CustomMetricValue}/>
        </Switch>
    </div>
);

export default Routes;
