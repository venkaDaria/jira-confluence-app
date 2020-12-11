import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import $ from 'jquery';

import { getEntities, deleteMetric, updateMetric, reset } from 'app/modules/metric/reducer';
import { IRootState } from 'app/shared/reducers';
import { renderToString } from 'react-dom/server';

export interface IMetricProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {
}

export class Index extends React.Component<IMetricProps> {
    componentWillUnmount() {
        $('.data-table-wrapper')
            .find('table')
            .DataTable()
            .destroy(true);
    }

    componentWillUpdate() {
        return this.props.metricList.length === 0;
    }

    componentDidMount() {
        const btn = $('.delete-btn');
        btn.on('click', () => deleteMetric(btn.name));

        const btn2 = $('.toggle-btn');
        btn2.on('click', () => toggleMetric(btn2.attr('data-row')));

        const btn3 = $('.rate_f');
        btn3.on('click', () => $('#rate_f' + count)
            .removeClass(' no-border')
            .prop('readonly', false));
    }

    render() {
        const { metricList, match } = this.props;

        if (metricList.length === 0) {
            this.props.getEntities();

            return <div/>;
        }

        return (
            <div>
                <h2>
                    Metrics
                </h2>
                <table className="table table-bordered table-hover" ref={elem => $(elem).DataTable({
                    data: metricList,
                    columns: columns(match)
                })}/>

                <br/>
                <h2>
                    Diagrams
                </h2>
                <a href={`#/diagrams`} color="link">
                    Go here
                </a>
                <hr/>
                <a href={'/api/metrics/confluence'}>Show confluence report</a>
                <br/>
                <br/>

                <h2>
                    Add a metric
                </h2>
                <form action={'/api/metrics'} method="POST">
                    <div className="form-group row">
                        <label className="col-1 col-form-label" htmlFor="name">Name</label>
                        <div className="col-8">
                            <input id="name" name="name" type="text" className="form-control"
                                   aria-describedby="nameHelpBlock" required />
                            <span id="nameHelpBlock" className="form-text text-muted">name of metric</span>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label htmlFor="rate" className="col-1 col-form-label">Rate</label>
                        <div className="col-8">
                            <input type="number" id="rate" name="rate" className="form-control" required
                                   aria-describedby="rateHelpBlock" />
                            <span id="rateHelpBlock" className="form-text text-muted">a fixed period in ms between invocations</span>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label htmlFor="jql" className="col-1 col-form-label">JQL</label>
                        <div className="col-8">
                            <input id="jql" name="jql" type="text" className="form-control" required
                                   aria-describedby="jqlHelpBlock" />
                            <span id="jqlHelpBlock" className="form-text text-muted">a JQL query for collecting this metric</span>
                        </div>
                    </div>
                    <div className="form-group row">
                        <div className="offset-1 col-8">
                            <button name="submit" type="submit" className="btn btn-primary">Submit</button>
                        </div>
                    </div>
                </form>

                <hr/>
                <Button tag={Link} to="/" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                </Button>
            </div>
        );
    }
}

const toggleMetric = row => {
    updateMetric(row, !row.enabled, row.rate);
};

const rateMetric = (row, rate) => {
    updateMetric(row, row.enabled, rate);
};

let count = 0;

const columns = match => [
    {
        data: 'id',
        title: 'ID'
    },
    {
        data: 'name',
        title: 'Name',
        render: name => renderToString(
            <a href={`#${match.url}/${name}`} color="link">
                {name}
            </a>
        )
    },
    {
        data: 'rate',
        title: 'Rate',
        render: (rate, type, row) => {
            count = count + 1;

            $(document).click(e => {
                if (!e.target.readOnly && e.target.id === 'rate_f' + count) {
                    const rate_f = $('#rate_f' + count);
                    rateMetric(row, rate_f.val());
                    rate_f
                        .addClass(' no-border')
                        .prop('readonly', true);
                }
            });

            return renderToString(
                <input className="rate_f no-border" id={'rate_f' + count} value={rate} data-row={row} />
            );
        }
    },
    {
        data: 'enabled',
        title: 'Enabled',
        render: (enabled, type, row) => enabled ? renderToString(
            <div className="toggle-btn" data-row={row}>
                <FontAwesomeIcon icon="check" />
            </div>
        ) : `<div />`
    },
    {
        data: 'custom',
        title: 'Delete',
        render: (custom, type, row) => custom ? renderToString(
            <Button name={row.metricName} color="danger" className="delete-btn">
                <FontAwesomeIcon icon="trash"/>
            </Button>
        ) : `<div />`
    }
];

const mapStateToProps = ({ metric }: IRootState) => ({
    metricList: metric.entities
});

const mapDispatchToProps = {
    getEntities,
    deleteMetric,
    updateMetric,
    reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Index);
