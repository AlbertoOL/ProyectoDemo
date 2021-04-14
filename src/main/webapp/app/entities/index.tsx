import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Doctor from './doctor';
import Hospital from './hospital';
import Paciente from './paciente';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}doctor`} component={Doctor} />
      <ErrorBoundaryRoute path={`${match.url}hospital`} component={Hospital} />
      <ErrorBoundaryRoute path={`${match.url}paciente`} component={Paciente} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
