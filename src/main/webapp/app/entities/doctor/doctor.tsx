import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './doctor.reducer';
import { IDoctor } from 'app/shared/model/doctor.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { Button as BS, Card, Image, Icon } from 'semantic-ui-react'

export interface IDoctorProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Doctor = (props: IDoctorProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { doctorList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="doctor-heading">
        <Translate contentKey="proyectoPracticaApp.doctor.home.title">Doctors</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="proyectoPracticaApp.doctor.home.createLabel">Create new Doctor</Translate>
        </Link>
      </h2>
      <div>
        <BS>PRUEBA</BS>
        <Card.Group>
        {doctorList.map((doctor, i) => (
         
          <Card key={doctor.claveRfc}>
            <Card.Content>
              <Icon name='flag'></Icon> 
              <Card.Header>{doctor.nombre}</Card.Header>
              <Card.Meta>{doctor.idHospital}</Card.Meta>
              <Card.Meta>{doctor.telefono}</Card.Meta>
              <Card.Meta>{doctor.email}</Card.Meta>
              <Card.Description>
                Especialidad: <strong>{doctor.especialidad}</strong>
              </Card.Description>
            </Card.Content>
            <Card.Content extra>
              <div className='ui two buttons'>
                <BS basic color='green'>
                  Approve
                </BS>
                <BS basic color='red'>
                  Decline
                </BS>
              </div>
            </Card.Content>
          </Card>

        ))}
        </Card.Group> 
      </div>
    </div>
  );
};

const mapStateToProps = ({ doctor }: IRootState) => ({
  doctorList: doctor.entities,
  loading: doctor.loading,
  totalItems: doctor.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Doctor);
