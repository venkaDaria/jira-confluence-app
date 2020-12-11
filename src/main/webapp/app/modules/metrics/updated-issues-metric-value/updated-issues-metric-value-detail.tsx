import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './updated-issues-metric-value.reducer';

import IssuesTable from 'app/shared/issues-table';
import { SprintIssues } from 'app/shared/model/metric.model';
import { convertDateTimeFromServer } from 'app/config/constants';

export interface IUpdatedIssuesMetricValueDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export class UpdatedIssuesMetricValueDetail extends React.Component<IUpdatedIssuesMetricValueDetailProps> {
    componentDidMount() {
        this.props.getEntity(this.props.match.params.id);
    }

    render() {
        const { updatedIssuesMetricValueEntity } = this.props;

        return (
            <div className="container">
                <Button tag={Link} to="/metrics/UPDATED_ISSUES" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                </Button>
                <h2>
                    UpdatedIssuesMetricValue [<b>{updatedIssuesMetricValueEntity.id}</b>]
                </h2>

                <dl className="row">
                    <dt className="col-sm-3">Calculation Time</dt>
                    <dd className="col-sm-9">{convertDateTimeFromServer(updatedIssuesMetricValueEntity.calculationTime)}</dd>

                    <dt className="col-sm-3">Result</dt>
                    <dd className="col-sm-9">
                        {updatedIssuesMetricValueEntity.result}
                    </dd>

                    <dt className="col-sm-3">Status</dt>
                    <dd className="col-sm-9">
                         <span className={updatedIssuesMetricValueEntity.status ?
                             updatedIssuesMetricValueEntity.status.toLowerCase() : ''}/>
                    </dd>

                    <dt className="col-sm-3 text-truncate">Metric</dt>
                    <dd className="col-sm-9">
                        <Link to={`/metrics`}>{updatedIssuesMetricValueEntity.metric}</Link>
                    </dd>

                </dl>

                <hr/>

                <span className="badge badge-secondary">Current Sprint issues</span>
                {updatedIssuesMetricValueEntity.currentIssues ?
                    <IssuesTable issues={updatedIssuesMetricValueEntity.currentIssues}/> : ''}
                <br/>
                <a href={`/api/reports/updated-issues/${updatedIssuesMetricValueEntity.id}?sprint=${SprintIssues.CURRENT}`}>Show
                    report</a>
                <br/>
                <a href={`/api/reports/updated-issues/${updatedIssuesMetricValueEntity.id}/mode2?sprint=${SprintIssues.CURRENT}`}>Show
                    report (mode 2)</a>
                <hr/>

                <span className="badge badge-secondary">Other issues</span>
                {updatedIssuesMetricValueEntity.otherIssues ?
                    <IssuesTable issues={updatedIssuesMetricValueEntity.otherIssues}/> : ''}
                <br/>
                <a href={`/api/reports/updated-issues/${updatedIssuesMetricValueEntity.id}?sprint=${SprintIssues.NOT_CURRENT}`}>Show
                    report</a>
                <br/>
                <a href={`/api/reports/updated-issues/${updatedIssuesMetricValueEntity.id}/mode2?sprint=${SprintIssues.NOT_CURRENT}`}>Show
                    report (mode 2)</a>
                <hr/>

                <Button tag={Link} to="/metrics/UPDATED_ISSUES" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                </Button>
            </div>
        );
    }
}

const mapStateToProps = ({ updatedIssuesMetricValue }: IRootState) => ({
    updatedIssuesMetricValueEntity: updatedIssuesMetricValue.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(UpdatedIssuesMetricValueDetail);
