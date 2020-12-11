import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './person-worklog-metric-value.reducer';

import { SprintIssues } from 'app/shared/model/metric.model';
import { convertDateTimeFromServer } from 'app/config/constants';

export interface IPersonWorklogMetricValueDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export class PersonWorklogMetricValueDetail extends React.Component<IPersonWorklogMetricValueDetailProps> {
    componentDidMount() {
        this.props.getEntity(this.props.match.params.id);
    }

    render() {
        const { personWorklogMetricValueEntity } = this.props;

        const date: Date = new Date();
        date.setDate(date.getDate() - 1);

        const yesterday: String = convertDateTimeFromServer(date);

        return (
            <div className="container">
                <Button tag={Link} to="/metrics/PERSON_WORKLOG" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                </Button>
                <h2>
                    PersonWorklogMetricValue [<b>{personWorklogMetricValueEntity.id}</b>]
                </h2>

                <dl className="row">
                    <dt className="col-sm-3">Calculation Time</dt>
                    <dd className="col-sm-9">{convertDateTimeFromServer(personWorklogMetricValueEntity.calculationTime)}</dd>

                    <dt className="col-sm-3">Result</dt>
                    <dd className="col-sm-9">
                        <ul>
                            <li>Should be reported in
                                sprint: {personWorklogMetricValueEntity.result &&
                                personWorklogMetricValueEntity.result.expectedSprintHours}</li>
                            <li>Reported in sprint: {personWorklogMetricValueEntity.result &&
                                personWorklogMetricValueEntity.result.actualSprintHours}</li>
                            <li>Should be reported
                                in {yesterday}: {personWorklogMetricValueEntity.result &&
                                personWorklogMetricValueEntity.result.expectedYesterdayHours}</li>
                            <li>Reported in {yesterday}: {personWorklogMetricValueEntity.result &&
                                personWorklogMetricValueEntity.result.actualYesterdayHours}</li>
                        </ul>
                    </dd>

                    <dt className="col-sm-3">Status</dt>
                    <dd className="col-sm-9">
                         <span className={personWorklogMetricValueEntity.status ?
                             personWorklogMetricValueEntity.status.toLowerCase() : ''}/>
                    </dd>

                    <dt className="col-sm-3 text-truncate">Metric</dt>
                    <dd className="col-sm-9">
                        <Link to={`/metrics`}>{personWorklogMetricValueEntity.metric}</Link>
                    </dd>

                </dl>

                <hr/>

                <span className="badge badge-secondary">User Info</span> {
                personWorklogMetricValueEntity.userInfos && personWorklogMetricValueEntity.userInfos.map(
                    (user, idx) => (
                        <div key={idx}>
                            <h4>{user.displayName}{user.login && ` (${user.login})`}: {
                                user.hoursSpent
                            } hours is reported</h4>
                            <span className={user.status ? user.status.toLowerCase() : ''}/>
                        </div>
                    )
                )
            }
                <br/>
                <a href={`/api/reports/person-worklog/${personWorklogMetricValueEntity.id}?sprint=${SprintIssues.CURRENT}`}>Show
                    report</a>
                <hr/>

                <Button tag={Link} to="/metrics/PERSON_WORKLOG" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                </Button>
            </div>
        );
    }
}

const mapStateToProps = ({ personWorklogMetricValue }: IRootState) => ({
    personWorklogMetricValueEntity: personWorklogMetricValue.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(PersonWorklogMetricValueDetail);
