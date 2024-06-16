import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MensajeDTO } from '../modelo/mensaje-dto';

@Injectable({
  providedIn: 'root'
})
export class ClinicaService {

  /**
   * URL 
   */
  private clinicaURL = "http://localhost:8080/api/clinica";

  constructor(private http: HttpClient) { }

  /**
  * Metodos que realizan las peticiones al servidor (GET, POST)
  */
  public listarCiudades(): Observable<MensajeDTO> {
    return this.http.get<MensajeDTO>(`${this.clinicaURL}/lista-ciudades`);
  }

  public listarEspecialidades(): Observable<MensajeDTO> {
    return this.http.get<MensajeDTO>(`${this.clinicaURL}/lista-especialidad`);
  }
  public listarTipoSangre(): Observable<MensajeDTO> {
    return this.http.get<MensajeDTO>(`${this.clinicaURL}/lista-tipo-sangre`);
  }
  public listarEPS(): Observable<MensajeDTO> {
    return this.http.get<MensajeDTO>(`${this.clinicaURL}/lista-eps`);
  }

  public listarMedicoPorEspecialidad(especialidad: String): Observable<MensajeDTO> {
    return this.http.get<MensajeDTO>(`${this.clinicaURL}/lista-medico-especialidad/${especialidad}`);
  }

  public listarHorariosMedico(codigoMedico: number): Observable<MensajeDTO> {
    return this.http.get<MensajeDTO>(`${this.clinicaURL}/listar-horario-medico/${codigoMedico}`);
  }
  public enviarLinkRecuperacion(email: string): Observable<MensajeDTO> {
    return this.http.post<MensajeDTO>(`${this.clinicaURL}/link-recuperacion-password`, email);
  }

}
