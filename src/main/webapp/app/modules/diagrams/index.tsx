import React from 'react';
import { connect } from 'react-redux';
import { Button } from 'reactstrap';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { BurndownChart, IDiagramProps } from 'app/modules/diagrams/burndown-chart';

import ReactChartkick from 'react-chartkick';
import Chart from 'chart.js';
import { IRootState } from 'app/shared/reducers';
import { getChartData, reset } from 'app/modules/diagrams/reducer';

ReactChartkick.addAdapter(Chart);

export class Index extends React.Component<IDiagramProps> {

    render() {
        return (
            <div>
                <h2>
                    Diagrams
                </h2>
                <BurndownChart {...this.props} />
                <Button tag={Link} to="/metrics" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                </Button>
            </div>
        );
    }
}

const mapStateToProps = ({ diagram }: IRootState) => ({
    diagram
});

const mapDispatchToProps = {
    getChartData,
    reset
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Index);
