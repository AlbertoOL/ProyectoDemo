import { EstatusRegistro } from 'app/shared/model/enumerations/estatus-registro.model';

export interface IDoctor {
  id?: number;
  claveRfc?: string;
  idHospital?: string;
  nombre?: string;
  telefono?: string;
  especialidad?: string;
  email?: string;
  estatus?: EstatusRegistro;
  hospitalIdHospital?: string;
  hospitalId?: number;
}

export const defaultValue: Readonly<IDoctor> = {};
