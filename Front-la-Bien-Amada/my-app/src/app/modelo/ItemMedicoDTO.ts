import { HorarioDTO } from "./HorarioDTO";

export interface ItemMedicoDTO {
    codigoMedico: number;
    nombreMedico: string;
    correoMedico: string;
    horarios: HorarioDTO[]; 
}