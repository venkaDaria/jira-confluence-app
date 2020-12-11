import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import metric, { MetricState } from 'app/modules/metric/reducer';

import customMetricValue, { CustomMetricValueState } from 'app/modules/metrics/custom-metric-value/custom-metric-value.reducer';
import notUpdatedIssuesMetricValue, { NotUpdatedIssuesMetricValueState } from 'app/modules/metrics/not-updated-issues-metric-value/not-updated-issues-metric-value.reducer';
import issuesPerPersonMetricValue, { IssuesPerPersonMetricValueState } from 'app/modules/metrics/issues-per-person-metric-value/issues-per-person-metric-value.reducer';
import updatedIssuesMetricValue, { UpdatedIssuesMetricValueState } from 'app/modules/metrics/updated-issues-metric-value/updated-issues-metric-value.reducer';
import personWorklogMetricValue, { PersonWorklogMetricValueState } from 'app/modules/metrics/person-worklog-metric-value/person-worklog-metric-value.reducer';
import diagram, { DiagramState } from 'app/modules/diagrams/reducer';

export interface IRootState {
    readonly loadingBar: any;
    readonly metric: MetricState;
    readonly customMetricValue: CustomMetricValueState;
    readonly notUpdatedIssuesMetricValue: NotUpdatedIssuesMetricValueState;
    readonly updatedIssuesMetricValue: UpdatedIssuesMetricValueState;
    readonly issuesPerPersonMetricValue: IssuesPerPersonMetricValueState;
    readonly personWorklogMetricValue: PersonWorklogMetricValueState;
    readonly diagram: DiagramState;
}

const rootReducer = combineReducers<IRootState>({
    loadingBar,
    metric,
    customMetricValue,
    notUpdatedIssuesMetricValue,
    issuesPerPersonMetricValue,
    personWorklogMetricValue,
    updatedIssuesMetricValue,
    diagram
});

export default rootReducer;
