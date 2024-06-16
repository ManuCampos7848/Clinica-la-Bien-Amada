import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from '../servicios/token.service';
import { AuthService } from '../servicios/auth.service';

const AUTHORIZATION = "Authorization";
const BEARER = "Bearer ";

@Injectable()
export class UsuarioInterceptor implements HttpInterceptor {
  constructor(private tokenService: TokenService, private authService: AuthService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Verifica si la solicitud es para los endpoints de autenticación o clínica
    const isApiUrl = req.url.includes("api/auth");
    const urlClinica = req.url.includes("api/clinica");

    // Si el usuario no ha iniciado sesión o la solicitud es para auth o clínica, no agregar el token
    if (!this.tokenService.isLogged() || isApiUrl || urlClinica) {
      return next.handle(req);
    }

    // Clona la solicitud para agregar la nueva cabecera
    let initReq = req;
    let token = this.tokenService.getToken();

    // Agrega el token a las cabeceras de la solicitud
    initReq = this.addToken(req, token!);

    // Pasa la solicitud clonada en lugar de la solicitud original
    return next.handle(initReq);
  }

  // Función auxiliar para agregar el token a la solicitud
  private addToken(req: HttpRequest<any>, token: string): HttpRequest<any> {
    return req.clone({ headers: req.headers.set(AUTHORIZATION, BEARER + token) });
  }
}
