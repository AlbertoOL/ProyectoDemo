import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './doctor.reducer';
import { IDoctor } from 'app/shared/model/doctor.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDoctorDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DoctorDetail = (props: IDoctorDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { doctorEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="proyectoPracticaApp.doctor.detail.title">Doctor</Translate> [<b>{doctorEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="claveRfc">
              <Translate contentKey="proyectoPracticaApp.doctor.claveRfc">Clave Rfc</Translate>
            </span>
            <UncontrolledTooltip target="claveRfc">
              <Translate contentKey="proyectoPracticaApp.doctor.help.claveRfc" />
            </UncontrolledTooltip>
          </dt>
          <dd>{doctorEntity.claveRfc}</dd>
          <dt>
            <span id="idHospital">
              <Translate contentKey="proyectoPracticaApp.doctor.idHospital">Id Hospital</Translate>
            </span>
            <UncontrolledTooltip target="idHospital">
              <Translate contentKey="proyectoPracticaApp.doctor.help.idHospital" />
            </UncontrolledTooltip>
          </dt>
          <dd>{doctorEntity.idHospital}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="proyectoPracticaApp.doctor.nombre">Nombre</Translate>
            </span>
            <UncontrolledTooltip target="nombre">
              <Translate contentKey="proyectoPracticaApp.doctor.help.nombre" />
            </UncontrolledTooltip>
          </dt>
          <dd>{doctorEntity.nombre}</dd>
          <dt>
            <span id="telefono">
              <Translate contentKey="proyectoPracticaApp.doctor.telefono">Telefono</Translate>
            </span>
            <UncontrolledTooltip target="telefono">
              <Translate contentKey="proyectoPracticaApp.doctor.help.telefono" />
            </UncontrolledTooltip>
          </dt>
          <dd>{doctorEntity.telefono}</dd>
          <dt>
            <span id="especialidad">
              <Translate contentKey="proyectoPracticaApp.doctor.especialidad">Especialidad</Translate>
            </span>
            <UncontrolledTooltip target="especialidad">
              <Translate contentKey="proyectoPracticaApp.doctor.help.especialidad" />
            </UncontrolledTooltip>
          </dt>
          <dd>{doctorEntity.especialidad}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="proyectoPracticaApp.doctor.email">Email</Translate>
            </span>
            <UncontrolledTooltip target="email">
              <Translate contentKey="proyectoPracticaApp.doctor.help.email" />
            </UncontrolledTooltip>
          </dt>
          <dd>{doctorEntity.email}</dd>
          <dt>
            <span id="estatus">
              <Translate contentKey="proyectoPracticaApp.doctor.estatus">Estatus</Translate>
            </span>
          </dt>
          <dd>{doctorEntity.estatus}</dd>
          <dt>
            <Translate contentKey="proyectoPracticaApp.doctor.hospital">Hospital</Translate>
          </dt>
          <dd>{doctorEntity.hospitalIdHospital ? doctorEntity.hospitalIdHospital : ''}</dd>
        </dl>
        <Button tag={Link} to="/doctor" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        <Button>
          Hola Mundo
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doctor/${doctorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ doctor }: IRootState) => ({
  doctorEntity: doctor.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DoctorDetail);
