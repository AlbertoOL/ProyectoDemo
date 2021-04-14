import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './hospital.reducer';
import { IHospital } from 'app/shared/model/hospital.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHospitalUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HospitalUpdate = (props: IHospitalUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { hospitalEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/hospital' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...hospitalEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="proyectoPracticaApp.hospital.home.createOrEditLabel">
            <Translate contentKey="proyectoPracticaApp.hospital.home.createOrEditLabel">Create or edit a Hospital</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : hospitalEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="hospital-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="hospital-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idHospitalLabel" for="hospital-idHospital">
                  <Translate contentKey="proyectoPracticaApp.hospital.idHospital">Id Hospital</Translate>
                </Label>
                <AvField
                  id="hospital-idHospital"
                  type="text"
                  name="idHospital"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="idHospitalLabel">
                  <Translate contentKey="proyectoPracticaApp.hospital.help.idHospital" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="nombreLabel" for="hospital-nombre">
                  <Translate contentKey="proyectoPracticaApp.hospital.nombre">Nombre</Translate>
                </Label>
                <AvField
                  id="hospital-nombre"
                  type="text"
                  name="nombre"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="nombreLabel">
                  <Translate contentKey="proyectoPracticaApp.hospital.help.nombre" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="numPisosLabel" for="hospital-numPisos">
                  <Translate contentKey="proyectoPracticaApp.hospital.numPisos">Num Pisos</Translate>
                </Label>
                <AvField
                  id="hospital-numPisos"
                  type="text"
                  name="numPisos"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="numPisosLabel">
                  <Translate contentKey="proyectoPracticaApp.hospital.help.numPisos" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="numCamasLabel" for="hospital-numCamas">
                  <Translate contentKey="proyectoPracticaApp.hospital.numCamas">Num Camas</Translate>
                </Label>
                <AvField
                  id="hospital-numCamas"
                  type="text"
                  name="numCamas"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="numCamasLabel">
                  <Translate contentKey="proyectoPracticaApp.hospital.help.numCamas" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="numCuartosLabel" for="hospital-numCuartos">
                  <Translate contentKey="proyectoPracticaApp.hospital.numCuartos">Num Cuartos</Translate>
                </Label>
                <AvField
                  id="hospital-numCuartos"
                  type="text"
                  name="numCuartos"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="numCuartosLabel">
                  <Translate contentKey="proyectoPracticaApp.hospital.help.numCuartos" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="fechaCreacionLabel" for="hospital-fechaCreacion">
                  <Translate contentKey="proyectoPracticaApp.hospital.fechaCreacion">Fecha Creacion</Translate>
                </Label>
                <AvField id="hospital-fechaCreacion" type="text" name="fechaCreacion" />
                <UncontrolledTooltip target="fechaCreacionLabel">
                  <Translate contentKey="proyectoPracticaApp.hospital.help.fechaCreacion" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="estatusLabel" for="hospital-estatus">
                  <Translate contentKey="proyectoPracticaApp.hospital.estatus">Estatus</Translate>
                </Label>
                <AvInput
                  id="hospital-estatus"
                  type="select"
                  className="form-control"
                  name="estatus"
                  value={(!isNew && hospitalEntity.estatus) || 'ACTIVO'}
                >
                  <option value="ACTIVO">{translate('proyectoPracticaApp.EstatusRegistro.ACTIVO')}</option>
                  <option value="DESACTIVADO">{translate('proyectoPracticaApp.EstatusRegistro.DESACTIVADO')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/hospital" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  hospitalEntity: storeState.hospital.entity,
  loading: storeState.hospital.loading,
  updating: storeState.hospital.updating,
  updateSuccess: storeState.hospital.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HospitalUpdate);
