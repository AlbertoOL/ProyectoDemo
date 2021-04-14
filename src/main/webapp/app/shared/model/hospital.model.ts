import { IDoctor } from 'app/shared/model/doctor.model';
import { IPaciente } from 'app/shared/model/paciente.model';
import { EstatusRegistro } from 'app/shared/model/enumerations/estatus-registro.model';

export interface IHospital {
  id?: number;
  idHospital?: string;
  nombre?: string;
  numPisos?: string;
  numCamas?: string;
  numCuartos?: string;
  fechaCreacion?: string;
  estatus?: EstatusRegistro;
  doctors?: IDoctor[];
  pacientes?: IPaciente[];
}

export const defaultValue: Readonly<IHospital> = {};
