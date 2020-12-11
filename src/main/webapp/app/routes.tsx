import React from 'react';
import { Switch } from 'react-router-dom';

import Home from 'app/modules/home';
import Diagrams from 'app/modules/diagrams';
import Metrics from 'app/modules/metrics';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

const Routes = () => (
    <div className="view-routes">
        <Switch>
            <ErrorBoundaryRoute path="/diagrams" component={Diagrams}/>
            <ErrorBoundaryRoute path="/metrics" component={Metrics}/>
            <ErrorBoundaryRoute path="/" component={Home}/>
        </Switch>
    </div>
);

export default Routes;
