import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button } from 'reactstrap';

import $ from 'jquery';

import { IRootState } from 'app/shared/reducers';
import { collect, getEntities } from './person-worklog-metric-value.reducer';
import { convertDateTimeFromServer } from 'app/config/constants';
import { renderToString } from 'react-dom/server';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export interface IPersonWorklogMetricValueProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {
}

export class PersonWorklogMetricValue extends React.Component<IPersonWorklogMetricValueProps> {
    componentWillUnmount() {
        $('.data-table-wrapper')
            .find('table')
            .DataTable()
            .destroy(true);
    }

    componentWillUpdate() {
        return this.props.personWorklogMetricValueList.length === 0;
    }

    collect = () => {
        this.props.collect('');
    };

    render() {
        const { personWorklogMetricValueList, match } = this.props;

        if (personWorklogMetricValueList.length === 0) {
            this.props.getEntities();

            return (
                <div>
                    <Button tag={Link} to="/metrics" replace color="info">
                        <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                    </Button>
                    &nbsp;
                    <Button onClick={this.collect} color="success">
                        <span className="d-none d-md-inline">Collect now</span>
                    </Button>
                </div>
            );
        }

        return (
            <div>
                <Button tag={Link} to="/metrics" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button onClick={this.collect} color="success">
                    <span className="d-none d-md-inline">Collect now</span>
                </Button>
                <h2 id="person-worklog-metric-value-heading">
                    <Link to={`/metrics`}>Person Worklog</Link>
                </h2>
                <table className="table table-bordered table-hover" ref={elem => $(elem).DataTable({
                    data: personWorklogMetricValueList,
                    columns: columns(match),
                    order: [[1, 'desc']]
                })}/>
                <Button tag={Link} to="/metrics" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                </Button>
            </div>
        );
    }
}

const columns = match => [
    {
        data: 'id',
        title: 'ID',
        render: id => renderToString(
            <a href={`#${match.url}/${id}`} color="link">
                {id}
            </a>
        )
    },
    {
        data: 'calculationTime',
        title: 'Calculation Time',
        render: time => convertDateTimeFromServer(time)
    },
    {
        data: 'result',
        title: 'Result',
        render: result => {
            const date: Date = new Date();
            date.setDate(date.getDate() - 1);

            const yesterday: String = convertDateTimeFromServer(date);

            return renderToString(
                <ul>
                    <li>Should be reported in sprint: {result.expectedSprintHours}</li>
                    <li>Reported in sprint: {result.actualSprintHours}</li>
                    <li>Should be reported in {yesterday}: {result.expectedYesterdayHours}</li>
                    <li>Reported in {yesterday}: {result.actualYesterdayHours}</li>
                </ul>
            );
        }
    },
    {
        data: 'status',
        title: 'Status',
        render: status => renderToString(
            <span className={status ? status.toLowerCase() : ''}/>
        )
    }
];

const mapStateToProps = ({ personWorklogMetricValue }: IRootState) => ({
    personWorklogMetricValueList: personWorklogMetricValue.entities
});

const mapDispatchToProps = {
    getEntities,
    collect
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(PersonWorklogMetricValue);
