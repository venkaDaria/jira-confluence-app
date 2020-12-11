import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button } from 'reactstrap';
import $ from 'jquery';

import { IRootState } from 'app/shared/reducers';
import { collect, getEntities } from './custom-metric-value.reducer';
import { convertDateTimeFromServer } from 'app/config/constants';
import { renderToString } from 'react-dom/server';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export interface ICustomMetricValueProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {
}

export class CustomMetricValue extends React.Component<ICustomMetricValueProps> {
    componentWillUnmount() {
        $('.data-table-wrapper')
            .find('table')
            .DataTable()
            .destroy(true);
    }

    componentWillUpdate() {
        return this.props.customMetricValueList.length === 0;
    }

    collect = () => {
        this.props.collect('');
    };

    render() {
        const { customMetricValueList, match } = this.props;

        if (customMetricValueList.length === 0) {
            // @ts-ignore
            this.props.getEntities(match.params.metricName);

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
                <h2 id="custom-metric-value-heading">
                    <Link to={`/metrics`}>Custom Metric Values</Link>
                </h2>
                <table className="table table-bordered table-hover" ref={elem => $(elem).DataTable({
                            data: customMetricValueList,
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
        title: 'Result'
    },
    {
        data: 'status',
        title: 'Status',
        render: status => renderToString(
            <span className={status ? status.toLowerCase() : ''}/>
        )
    }
];

const mapStateToProps = ({ customMetricValue }: IRootState) => ({
    customMetricValueList: customMetricValue.entities
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
)(CustomMetricValue);
