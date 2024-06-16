import { Injectable, inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { TokenService } from '../servicios/token.service';

@Injectable({
  providedIn: 'root'
})
export class RolesService {
  // Variable para almacenar el rol real del usuario
  realRole: string = "";

  constructor(private tokenService: TokenService, private router: Router) { }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    // Obtiene los roles esperados desde la configuración de la ruta
    const expectedRole: string[] = next.data["expectedRole"];
    // Obtiene el rol real del usuario desde el TokenService
    this.realRole = this.tokenService.getRole();

    // Verifica si el usuario no está autenticado o no tiene ninguno de los roles esperados
    if (!this.tokenService.isLogged() || !expectedRole.some(r => this.realRole.includes(r))) {
      // Si no está autenticado o no tiene el rol adecuado, redirige a la página principal
      this.router.navigate([""]);
      // Retorna false para bloquear el acceso a la ruta protegida
      return false;
    }
    // Si el usuario está autenticado y tiene el rol adecuado, permite el acceso a la ruta
    return true;
  }
}

// Guard para verificar si el usuario puede activar una ruta en función de su rol
export const RolesGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
  return inject(RolesService).canActivate(next, state);
}
