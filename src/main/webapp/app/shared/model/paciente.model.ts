import { EstatusRegistro } from 'app/shared/model/enumerations/estatus-registro.model';

export interface IPaciente {
  id?: number;
  nss?: string;
  idHospital?: string;
  nombre?: string;
  apPaterno?: string;
  apMaterno?: string;
  fechaNac?: string;
  estatus?: EstatusRegistro;
  hospitalIdHospital?: string;
  hospitalId?: number;
}

export const defaultValue: Readonly<IPaciente> = {};
