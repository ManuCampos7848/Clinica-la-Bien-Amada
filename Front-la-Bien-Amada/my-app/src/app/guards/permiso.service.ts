import { Injectable, inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { TokenService } from '../servicios/token.service';

@Injectable({
  providedIn: 'root'
})
export class PermisoService {
  constructor(private tokenService: TokenService, private router: Router) { }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    // Verifica si el usuario está autenticado
    if (this.tokenService.isLogged()) {
      // Si está autenticado, redirige a la página principal
      this.router.navigate([""]);
      // Retorna false para bloquear el acceso a la ruta protegida
      return false;
    }
    // Si no está autenticado, permite el acceso a la ruta
    return true;
  }
}

// Guard para verificar si el usuario puede activar una ruta
export const LoginGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
  return inject(PermisoService).canActivate(next, state);
}
