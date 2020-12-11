import React from 'react';
import { connect } from 'react-redux';

import { LineChart } from 'react-chartkick';
import { IRootState } from 'app/shared/reducers';
import { getChartData, reset } from 'app/modules/diagrams/reducer';
import { RouteComponentProps } from 'react-router';
import { Diagram } from 'app/shared/model/diagram.model';

export interface IDiagramProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {
}

export class BurndownChart extends React.Component<IDiagramProps> {

    componentDidMount() {
        this.props.getChartData(Diagram.BURNDOWN);
    }

    render() {
        const { dataChart } = this.props.diagram;

        const chartData = [
            {
                name: 'remaining',
                data: dataChart ? dataChart.remaining : []
            }, {
                name: 'planned',
                data: dataChart ? dataChart.stories : []
            }
        ];

        return (
            <LineChart data={chartData}
                       curve={false} legend messages={{ empty: 'No data' }} download/>
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

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(BurndownChart);
