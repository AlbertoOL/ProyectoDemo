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
import { getEntity, updateEntity, createEntity, reset } from './paciente.reducer';
import { IPaciente } from 'app/shared/model/paciente.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPacienteUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PacienteUpdate = (props: IPacienteUpdateProps) => {
  const [hospitalId, setHospitalId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { pacienteEntity, hospitals, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/paciente' + props.location.search);
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
        ...pacienteEntity,
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
          <h2 id="proyectoPracticaApp.paciente.home.createOrEditLabel">
            <Translate contentKey="proyectoPracticaApp.paciente.home.createOrEditLabel">Create or edit a Paciente</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : pacienteEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="paciente-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="paciente-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nssLabel" for="paciente-nss">
                  <Translate contentKey="proyectoPracticaApp.paciente.nss">Nss</Translate>
                </Label>
                <AvField
                  id="paciente-nss"
                  type="text"
                  name="nss"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="nssLabel">
                  <Translate contentKey="proyectoPracticaApp.paciente.help.nss" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="idHospitalLabel" for="paciente-idHospital">
                  <Translate contentKey="proyectoPracticaApp.paciente.idHospital">Id Hospital</Translate>
                </Label>
                <AvField
                  id="paciente-idHospital"
                  type="text"
                  name="idHospital"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="idHospitalLabel">
                  <Translate contentKey="proyectoPracticaApp.paciente.help.idHospital" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="nombreLabel" for="paciente-nombre">
                  <Translate contentKey="proyectoPracticaApp.paciente.nombre">Nombre</Translate>
                </Label>
                <AvField
                  id="paciente-nombre"
                  type="text"
                  name="nombre"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="nombreLabel">
                  <Translate contentKey="proyectoPracticaApp.paciente.help.nombre" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="apPaternoLabel" for="paciente-apPaterno">
                  <Translate contentKey="proyectoPracticaApp.paciente.apPaterno">Ap Paterno</Translate>
                </Label>
                <AvField
                  id="paciente-apPaterno"
                  type="text"
                  name="apPaterno"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="apPaternoLabel">
                  <Translate contentKey="proyectoPracticaApp.paciente.help.apPaterno" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="apMaternoLabel" for="paciente-apMaterno">
                  <Translate contentKey="proyectoPracticaApp.paciente.apMaterno">Ap Materno</Translate>
                </Label>
                <AvField
                  id="paciente-apMaterno"
                  type="text"
                  name="apMaterno"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="apMaternoLabel">
                  <Translate contentKey="proyectoPracticaApp.paciente.help.apMaterno" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="fechaNacLabel" for="paciente-fechaNac">
                  <Translate contentKey="proyectoPracticaApp.paciente.fechaNac">Fecha Nac</Translate>
                </Label>
                <AvField
                  id="paciente-fechaNac"
                  type="text"
                  name="fechaNac"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="fechaNacLabel">
                  <Translate contentKey="proyectoPracticaApp.paciente.help.fechaNac" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="estatusLabel" for="paciente-estatus">
                  <Translate contentKey="proyectoPracticaApp.paciente.estatus">Estatus</Translate>
                </Label>
                <AvInput
                  id="paciente-estatus"
                  type="select"
                  className="form-control"
                  name="estatus"
                  value={(!isNew && pacienteEntity.estatus) || 'ACTIVO'}
                >
                  <option value="ACTIVO">{translate('proyectoPracticaApp.EstatusRegistro.ACTIVO')}</option>
                  <option value="DESACTIVADO">{translate('proyectoPracticaApp.EstatusRegistro.DESACTIVADO')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="paciente-hospital">
                  <Translate contentKey="proyectoPracticaApp.paciente.hospital">Hospital</Translate>
                </Label>
                <AvInput id="paciente-hospital" type="select" className="form-control" name="hospitalId">
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
              <Button tag={Link} id="cancel-save" to="/paciente" replace color="info">
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
  pacienteEntity: storeState.paciente.entity,
  loading: storeState.paciente.loading,
  updating: storeState.paciente.updating,
  updateSuccess: storeState.paciente.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(PacienteUpdate);
