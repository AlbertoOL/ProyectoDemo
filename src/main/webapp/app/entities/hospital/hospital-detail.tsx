import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './hospital.reducer';
import { IHospital } from 'app/shared/model/hospital.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHospitalDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HospitalDetail = (props: IHospitalDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { hospitalEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="proyectoPracticaApp.hospital.detail.title">Hospital</Translate> [<b>{hospitalEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="idHospital">
              <Translate contentKey="proyectoPracticaApp.hospital.idHospital">Id Hospital</Translate>
            </span>
            <UncontrolledTooltip target="idHospital">
              <Translate contentKey="proyectoPracticaApp.hospital.help.idHospital" />
            </UncontrolledTooltip>
          </dt>
          <dd>{hospitalEntity.idHospital}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="proyectoPracticaApp.hospital.nombre">Nombre</Translate>
            </span>
            <UncontrolledTooltip target="nombre">
              <Translate contentKey="proyectoPracticaApp.hospital.help.nombre" />
            </UncontrolledTooltip>
          </dt>
          <dd>{hospitalEntity.nombre}</dd>
          <dt>
            <span id="numPisos">
              <Translate contentKey="proyectoPracticaApp.hospital.numPisos">Num Pisos</Translate>
            </span>
            <UncontrolledTooltip target="numPisos">
              <Translate contentKey="proyectoPracticaApp.hospital.help.numPisos" />
            </UncontrolledTooltip>
          </dt>
          <dd>{hospitalEntity.numPisos}</dd>
          <dt>
            <span id="numCamas">
              <Translate contentKey="proyectoPracticaApp.hospital.numCamas">Num Camas</Translate>
            </span>
            <UncontrolledTooltip target="numCamas">
              <Translate contentKey="proyectoPracticaApp.hospital.help.numCamas" />
            </UncontrolledTooltip>
          </dt>
          <dd>{hospitalEntity.numCamas}</dd>
          <dt>
            <span id="numCuartos">
              <Translate contentKey="proyectoPracticaApp.hospital.numCuartos">Num Cuartos</Translate>
            </span>
            <UncontrolledTooltip target="numCuartos">
              <Translate contentKey="proyectoPracticaApp.hospital.help.numCuartos" />
            </UncontrolledTooltip>
          </dt>
          <dd>{hospitalEntity.numCuartos}</dd>
          <dt>
            <span id="fechaCreacion">
              <Translate contentKey="proyectoPracticaApp.hospital.fechaCreacion">Fecha Creacion</Translate>
            </span>
            <UncontrolledTooltip target="fechaCreacion">
              <Translate contentKey="proyectoPracticaApp.hospital.help.fechaCreacion" />
            </UncontrolledTooltip>
          </dt>
          <dd>{hospitalEntity.fechaCreacion}</dd>
          <dt>
            <span id="estatus">
              <Translate contentKey="proyectoPracticaApp.hospital.estatus">Estatus</Translate>
            </span>
          </dt>
          <dd>{hospitalEntity.estatus}</dd>
        </dl>
        <Button tag={Link} to="/hospital" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hospital/${hospitalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ hospital }: IRootState) => ({
  hospitalEntity: hospital.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HospitalDetail);
