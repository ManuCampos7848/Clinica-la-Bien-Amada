import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MensajeDTO } from '../modelo/mensaje-dto';
import { DiaLibreDTO } from '../modelo/DiaLibreDTO';
import { RegistroAtencionDTO } from '../modelo/RegistroAtencionDTO';

@Injectable({
  providedIn: 'root'
})
export class MedicoService {

  /**
   * URL 
   */
  private userUrl = "http://localhost:8080/api/medicos";
  
  constructor(private http: HttpClient) { }

  /**
  * Metodos que realizan las peticiones al servidor (GET, POST)
  */
  public listarCitasPendientes(codigoMedico: number): Observable<MensajeDTO> {
    return this.http.get<MensajeDTO>(`${this.userUrl}/citas-pendientes/${codigoMedico}`);
  }
  public listarCitasRealizadas(codigoMedico: number): Observable<MensajeDTO> {
    return this.http.get<MensajeDTO>(`${this.userUrl}/citas-realizadas/${codigoMedico}`);
  }
  public listarCitasCanceladas(codigoMedico: number): Observable<MensajeDTO> {
    return this.http.get<MensajeDTO>(`${this.userUrl}/citas-canceladas/${codigoMedico}`);
  }
  public listarDiasLibres(codigoMedico: number): Observable<MensajeDTO> {
    return this.http.get<MensajeDTO>(`${this.userUrl}/listar-dias-libres/${codigoMedico}`);
  }
  public agendarDiaLibre(diaLibreDTO: DiaLibreDTO): Observable<MensajeDTO> {
    return this.http.post<MensajeDTO>(`${this.userUrl}/agendar-dia-libre`, diaLibreDTO);
  }
  public atenderCita(registroAtencionDTO: RegistroAtencionDTO): Observable<MensajeDTO> {
    return this.http.post<MensajeDTO>(`${this.userUrl}/atender-cita`, registroAtencionDTO);
  }
  public verDetalleCita(codigoCita: number): Observable<MensajeDTO> {
    return this.http.get<MensajeDTO>(`${this.userUrl}/detalle-cita/${codigoCita}`);
  }
  public listarHistorialAtenciones(codigoCita: number): Observable<MensajeDTO> {
    return this.http.get<MensajeDTO>(`${this.userUrl}/atenciones/${codigoCita}`);
  }
  public verDetalleAtencion(codigoCita: number): Observable<MensajeDTO> {
    return this.http.get<MensajeDTO>(`${this.userUrl}/detalle-atencion/${codigoCita}`);
  }
}
