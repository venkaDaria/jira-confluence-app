import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button } from 'reactstrap';

import $ from 'jquery';

import { IRootState } from 'app/shared/reducers';
import { collect, getEntities } from './issues-per-person-metric-value.reducer';
import { convertDateTimeFromServer } from 'app/config/constants';
import { renderToString } from 'react-dom/server';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IssueStatus } from 'app/shared/model/metric.model';

export interface IssuesPerPersonMetricValueProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {
}

export class IssuesPerPersonMetricValue extends React.Component<IssuesPerPersonMetricValueProps> {
    componentWillUnmount() {
        $('.data-table-wrapper')
            .find('table')
            .DataTable()
            .destroy(true);
    }

    componentWillUpdate() {
        return this.props.issuesPerPersonMetricValueList.length === 0;
    }

    collect = () => {
        this.props.collect('');
    };

    render() {
        const { issuesPerPersonMetricValueList, match } = this.props;

        if (issuesPerPersonMetricValueList.length === 0) {
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
                <h2 id="issues-per-person-metric-value-heading">
                    <Link to={`/metrics`}>Issues Per Person Metric Values</Link>
                </h2>
                <table className="table table-bordered table-hover" ref={elem => $(elem).DataTable({
                    data: issuesPerPersonMetricValueList,
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
        render: (result, type, row) => `${result[IssueStatus.ALL]} issues for ${row.userInfos.length} users`
    },
    {
        data: 'status',
        title: 'Status',
        render: status => renderToString(
            <span className={status ? status.toLowerCase() : ''}/>
        )
    }
];

const mapStateToProps = ({ issuesPerPersonMetricValue }: IRootState) => ({
    issuesPerPersonMetricValueList: issuesPerPersonMetricValue.entities
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
)(IssuesPerPersonMetricValue);
