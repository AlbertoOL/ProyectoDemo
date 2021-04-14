import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Paciente from './paciente';
import PacienteDetail from './paciente-detail';
import PacienteUpdate from './paciente-update';
import PacienteDeleteDialog from './paciente-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PacienteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PacienteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PacienteDetail} />
      <ErrorBoundaryRoute path={match.url} component={Paciente} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PacienteDeleteDialog} />
  </>
);

export default Routes;
