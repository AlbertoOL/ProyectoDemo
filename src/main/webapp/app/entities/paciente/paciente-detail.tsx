import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './paciente.reducer';
import { IPaciente } from 'app/shared/model/paciente.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPacienteDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PacienteDetail = (props: IPacienteDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { pacienteEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="proyectoPracticaApp.paciente.detail.title">Paciente</Translate> [<b>{pacienteEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nss">
              <Translate contentKey="proyectoPracticaApp.paciente.nss">Nss</Translate>
            </span>
            <UncontrolledTooltip target="nss">
              <Translate contentKey="proyectoPracticaApp.paciente.help.nss" />
            </UncontrolledTooltip>
          </dt>
          <dd>{pacienteEntity.nss}</dd>
          <dt>
            <span id="idHospital">
              <Translate contentKey="proyectoPracticaApp.paciente.idHospital">Id Hospital</Translate>
            </span>
            <UncontrolledTooltip target="idHospital">
              <Translate contentKey="proyectoPracticaApp.paciente.help.idHospital" />
            </UncontrolledTooltip>
          </dt>
          <dd>{pacienteEntity.idHospital}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="proyectoPracticaApp.paciente.nombre">Nombre</Translate>
            </span>
            <UncontrolledTooltip target="nombre">
              <Translate contentKey="proyectoPracticaApp.paciente.help.nombre" />
            </UncontrolledTooltip>
          </dt>
          <dd>{pacienteEntity.nombre}</dd>
          <dt>
            <span id="apPaterno">
              <Translate contentKey="proyectoPracticaApp.paciente.apPaterno">Ap Paterno</Translate>
            </span>
            <UncontrolledTooltip target="apPaterno">
              <Translate contentKey="proyectoPracticaApp.paciente.help.apPaterno" />
            </UncontrolledTooltip>
          </dt>
          <dd>{pacienteEntity.apPaterno}</dd>
          <dt>
            <span id="apMaterno">
              <Translate contentKey="proyectoPracticaApp.paciente.apMaterno">Ap Materno</Translate>
            </span>
            <UncontrolledTooltip target="apMaterno">
              <Translate contentKey="proyectoPracticaApp.paciente.help.apMaterno" />
            </UncontrolledTooltip>
          </dt>
          <dd>{pacienteEntity.apMaterno}</dd>
          <dt>
            <span id="fechaNac">
              <Translate contentKey="proyectoPracticaApp.paciente.fechaNac">Fecha Nac</Translate>
            </span>
            <UncontrolledTooltip target="fechaNac">
              <Translate contentKey="proyectoPracticaApp.paciente.help.fechaNac" />
            </UncontrolledTooltip>
          </dt>
          <dd>{pacienteEntity.fechaNac}</dd>
          <dt>
            <span id="estatus">
              <Translate contentKey="proyectoPracticaApp.paciente.estatus">Estatus</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.estatus}</dd>
          <dt>
            <Translate contentKey="proyectoPracticaApp.paciente.hospital">Hospital</Translate>
          </dt>
          <dd>{pacienteEntity.hospitalIdHospital ? pacienteEntity.hospitalIdHospital : ''}</dd>
        </dl>
        <Button tag={Link} to="/paciente" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/paciente/${pacienteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ paciente }: IRootState) => ({
  pacienteEntity: paciente.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PacienteDetail);
