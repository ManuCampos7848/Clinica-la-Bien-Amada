import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegistroPacienteDTO } from '../modelo/registro-paciente-dto';
import { LoginDTO } from '../modelo/login-dto';
import { MensajeDTO } from '../modelo/mensaje-dto';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
 
  /**
   * URL 
   */
  private authURL = "http://localhost:8080/api/auth";
  constructor(private http: HttpClient) { }

  /**
   * Metodos que realizan las peticiones al servidor (POST)
   */
  public registrarPaciente(paciente: RegistroPacienteDTO): Observable<MensajeDTO> {
    return this.http.post<MensajeDTO>(`${this.authURL}/registrar-paciente`, paciente);
  }
  public login(loginDTO: LoginDTO): Observable<MensajeDTO> {
    return this.http.post<MensajeDTO>(`${this.authURL}/login`, loginDTO);
  }
}
