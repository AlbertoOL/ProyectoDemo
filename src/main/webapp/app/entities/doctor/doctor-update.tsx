import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IHospital } from 'app/shared/model/hospital.model';
import { getEntities as getHospitals } from 'app/entities/hospital/hospital.reducer';
import { getEntity, updateEntity, createEntity, reset } from './doctor.reducer';
import { IDoctor } from 'app/shared/model/doctor.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDoctorUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DoctorUpdate = (props: IDoctorUpdateProps) => {
  const [hospitalId, setHospitalId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { doctorEntity, hospitals, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/doctor' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getHospitals();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...doctorEntity,
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
          <h2 id="proyectoPracticaApp.doctor.home.createOrEditLabel">
            <Translate contentKey="proyectoPracticaApp.doctor.home.createOrEditLabel">Create or edit a Doctor</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : doctorEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="doctor-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="doctor-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="claveRfcLabel" for="doctor-claveRfc">
                  <Translate contentKey="proyectoPracticaApp.doctor.claveRfc">Clave Rfc</Translate>
                </Label>
                <AvField
                  id="doctor-claveRfc"
                  type="text"
                  name="claveRfc"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="claveRfcLabel">
                  <Translate contentKey="proyectoPracticaApp.doctor.help.claveRfc" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="idHospitalLabel" for="doctor-idHospital">
                  <Translate contentKey="proyectoPracticaApp.doctor.idHospital">Id Hospital</Translate>
                </Label>
                <AvField
                  id="doctor-idHospital"
                  type="text"
                  name="idHospital"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="idHospitalLabel">
                  <Translate contentKey="proyectoPracticaApp.doctor.help.idHospital" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="nombreLabel" for="doctor-nombre">
                  <Translate contentKey="proyectoPracticaApp.doctor.nombre">Nombre</Translate>
                </Label>
                <AvField
                  id="doctor-nombre"
                  type="text"
                  name="nombre"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="nombreLabel">
                  <Translate contentKey="proyectoPracticaApp.doctor.help.nombre" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="telefonoLabel" for="doctor-telefono">
                  <Translate contentKey="proyectoPracticaApp.doctor.telefono">Telefono</Translate>
                </Label>
                <AvField
                  id="doctor-telefono"
                  type="text"
                  name="telefono"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="telefonoLabel">
                  <Translate contentKey="proyectoPracticaApp.doctor.help.telefono" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="especialidadLabel" for="doctor-especialidad">
                  <Translate contentKey="proyectoPracticaApp.doctor.especialidad">Especialidad</Translate>
                </Label>
                <AvField
                  id="doctor-especialidad"
                  type="text"
                  name="especialidad"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="especialidadLabel">
                  <Translate contentKey="proyectoPracticaApp.doctor.help.especialidad" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="doctor-email">
                  <Translate contentKey="proyectoPracticaApp.doctor.email">Email</Translate>
                </Label>
                <AvField id="doctor-email" type="text" name="email" />
                <UncontrolledTooltip target="emailLabel">
                  <Translate contentKey="proyectoPracticaApp.doctor.help.email" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="estatusLabel" for="doctor-estatus">
                  <Translate contentKey="proyectoPracticaApp.doctor.estatus">Estatus</Translate>
                </Label>
                <AvInput
                  id="doctor-estatus"
                  type="select"
                  className="form-control"
                  name="estatus"
                  value={(!isNew && doctorEntity.estatus) || 'ACTIVO'}
                >
                  <option value="ACTIVO">{translate('proyectoPracticaApp.EstatusRegistro.ACTIVO')}</option>
                  <option value="DESACTIVADO">{translate('proyectoPracticaApp.EstatusRegistro.DESACTIVADO')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="doctor-hospital">
                  <Translate contentKey="proyectoPracticaApp.doctor.hospital">Hospital</Translate>
                </Label>
                <AvInput id="doctor-hospital" type="select" className="form-control" name="hospitalId">
                  <option value="" key="0" />
                  {hospitals
                    ? hospitals.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.idHospital}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/doctor" replace color="info">
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
  hospitals: storeState.hospital.entities,
  doctorEntity: storeState.doctor.entity,
  loading: storeState.doctor.loading,
  updating: storeState.doctor.updating,
  updateSuccess: storeState.doctor.updateSuccess,
});

const mapDispatchToProps = {
  getHospitals,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DoctorUpdate);
