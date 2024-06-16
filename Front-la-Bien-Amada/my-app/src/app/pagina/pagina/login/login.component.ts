import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Alerta } from 'src/app/modelo/alerta';
import { LoginDTO } from 'src/app/modelo/login-dto';
import { AuthService } from 'src/app/servicios/auth.service';
import { ClinicaService } from 'src/app/servicios/clinica.service';
import { TokenService } from 'src/app/servicios/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  // Instancia del objeto de datos de inicio de sesión
  loginDTO: LoginDTO;
  // Alerta para mostrar mensajes al usuario
  alerta!: Alerta;
  // Correo electrónico ingresado por el usuario para recuperación de contraseña
  correo: string = '';

  constructor(private authService: AuthService, private tokenService: TokenService,
    private clinicaService: ClinicaService, private router: Router) {

    // Inicializa el objeto de datos de inicio de sesión
    this.loginDTO = new LoginDTO();
  }

  // Método para manejar el inicio de sesión
  public login() {
    // Llama al servicio de autenticación para iniciar sesión
    this.authService.login(this.loginDTO).subscribe({
      next: (data: { respuesta: { token: any; }; }) => {
        // Si el inicio de sesión es exitoso, guarda el token en el TokenService
        this.tokenService.login(data.respuesta.token);
      },
      error: (error: { error: { respuesta: any; }; }) => {
        // Si hay un error, muestra una alerta con el mensaje de error
        this.alerta = { mensaje: error.error.respuesta, tipo: "danger" };
      }
    });
  }

}
