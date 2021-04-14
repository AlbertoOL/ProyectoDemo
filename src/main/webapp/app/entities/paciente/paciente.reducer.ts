import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPaciente, defaultValue } from 'app/shared/model/paciente.model';

export const ACTION_TYPES = {
  FETCH_PACIENTE_LIST: 'paciente/FETCH_PACIENTE_LIST',
  FETCH_PACIENTE: 'paciente/FETCH_PACIENTE',
  CREATE_PACIENTE: 'paciente/CREATE_PACIENTE',
  UPDATE_PACIENTE: 'paciente/UPDATE_PACIENTE',
  DELETE_PACIENTE: 'paciente/DELETE_PACIENTE',
  RESET: 'paciente/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPaciente>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PacienteState = Readonly<typeof initialState>;

// Reducer

export default (state: PacienteState = initialState, action): PacienteState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PACIENTE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PACIENTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PACIENTE):
    case REQUEST(ACTION_TYPES.UPDATE_PACIENTE):
    case REQUEST(ACTION_TYPES.DELETE_PACIENTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PACIENTE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PACIENTE):
    case FAILURE(ACTION_TYPES.CREATE_PACIENTE):
    case FAILURE(ACTION_TYPES.UPDATE_PACIENTE):
    case FAILURE(ACTION_TYPES.DELETE_PACIENTE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PACIENTE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PACIENTE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PACIENTE):
    case SUCCESS(ACTION_TYPES.UPDATE_PACIENTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PACIENTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/pacientes';

// Actions

export const getEntities: ICrudGetAllAction<IPaciente> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PACIENTE_LIST,
    payload: axios.get<IPaciente>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPaciente> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PACIENTE,
    payload: axios.get<IPaciente>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPaciente> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PACIENTE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPaciente> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PACIENTE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPaciente> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PACIENTE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
