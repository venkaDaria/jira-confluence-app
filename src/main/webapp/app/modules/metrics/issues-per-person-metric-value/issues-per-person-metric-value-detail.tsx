import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './issues-per-person-metric-value.reducer';

import IssuesTable from 'app/shared/issues-table';
import { IssueStatus, SprintIssues } from 'app/shared/model/metric.model';
import { convertDateTimeFromServer } from 'app/config/constants';

export interface IssuesPerPersonMetricValueDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export class IssuesPerPersonMetricValueDetail extends React.Component<IssuesPerPersonMetricValueDetailProps> {
    componentDidMount() {
        this.props.getEntity(this.props.match.params.id);
    }

    getLength = arr => arr ? arr.length : 0;

    render() {
        const { issuesPerPersonMetricValueEntity } = this.props;

        return (
            <div className="container">
                <Button tag={Link} to="/metrics/ISSUES_PER_PERSON" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                </Button>
                <h2>
                    IssuesPerPersonMetricValue [<b>{issuesPerPersonMetricValueEntity.id}</b>]
                </h2>

                <dl className="row">
                    <dt className="col-sm-3">Calculation Time</dt>
                    <dd className="col-sm-9">{convertDateTimeFromServer(issuesPerPersonMetricValueEntity.calculationTime)}</dd>

                    <dt className="col-sm-3">Result</dt>
                    <dd className="col-sm-9">
                        <p>Users count: {this.getLength(issuesPerPersonMetricValueEntity.userInfos)}</p>
                        <p>Average issues per user: {issuesPerPersonMetricValueEntity.result &&
                        issuesPerPersonMetricValueEntity.result[IssueStatus.ALL]
                        / this.getLength(issuesPerPersonMetricValueEntity.userInfos)}</p>
                        {
                            issuesPerPersonMetricValueEntity.result &&
                            Object.entries(issuesPerPersonMetricValueEntity.result).map(
                                ([key, value]) => <p key={key + value}>{key} : {value}</p>
                            )
                        }
                    </dd>

                    <dt className="col-sm-3">Status</dt>
                    <dd className="col-sm-9">
                         <span className={issuesPerPersonMetricValueEntity.status ?
                             issuesPerPersonMetricValueEntity.status.toLowerCase() : ''}/>
                    </dd>

                    <dt className="col-sm-3 text-truncate">Metric</dt>
                    <dd className="col-sm-9">
                        <Link to={`/metrics`}>{issuesPerPersonMetricValueEntity.metric}</Link>
                    </dd>

                </dl>

                <hr/>

                <span className="badge badge-secondary">User Info</span> {
                issuesPerPersonMetricValueEntity.userInfos && issuesPerPersonMetricValueEntity.userInfos.map(
                    (user, idx) => (
                        <div key={idx}>
                            <h4>{user.displayName}{user.login && ` (${user.login})`}: {
                                this.getLength(user.issues.filter(issue =>
                                    issue.status === IssueStatus.IN_PROGRESS
                                ))
                            } issue(s) in progress</h4>
                            <span className={user.status ? user.status.toLowerCase() : ''}/>
                            {
                                this.getLength(user.issues) !== 0 ?
                                    <IssuesTable issues={user.issues}/> :
                                    ''
                            }
                        </div>
                    )
                )
            }
                <br/>
                <a href={`/api/reports/issues-per-person/${issuesPerPersonMetricValueEntity.id}?sprint=${SprintIssues.CURRENT}`}>Show
                    report</a>
                <hr/>

                <Button tag={Link} to="/metrics/ISSUES_PER_PERSON" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                </Button>
            </div>
        );
    }
}

const mapStateToProps = ({ issuesPerPersonMetricValue }: IRootState) => ({
    issuesPerPersonMetricValueEntity: issuesPerPersonMetricValue.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(IssuesPerPersonMetricValueDetail);
