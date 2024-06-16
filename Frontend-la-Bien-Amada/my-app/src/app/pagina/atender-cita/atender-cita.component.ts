import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RegistroAtencionDTO } from 'src/app/modelo/RegistroAtencionDTO';
import { Alerta } from 'src/app/modelo/alerta';
import { MedicoService } from 'src/app/servicios/medico.service';
import { TokenService } from 'src/app/servicios/token.service';

@Component({
  selector: 'app-atender-cita',
  templateUrl: './atender-cita.component.html',
  styleUrls: ['./atender-cita.component.css']
})
export class AtenderCitaComponent {

  registroAtencionDTO: RegistroAtencionDTO; // Objeto para almacenar los datos del registro de atención.
  alerta!: Alerta; // Objeto para manejar mensajes de alerta.
  cita: number = 0; // Variable para almacenar el código de la cita a atender.

  constructor(private tokenService: TokenService, private route: ActivatedRoute,
              private medicoService: MedicoService) {
    // Suscripción a los parámetros de la ruta para obtener el código de la cita.
    this.route.params.subscribe(params => {
      this.cita = params['codigoCita']; // Obtener el código de la cita de los parámetros de la ruta.
    });

    this.registroAtencionDTO = new RegistroAtencionDTO(); // Inicialización del objeto de registro de atención.
    
    this.registroAtencionDTO.codigoCita = this.cita; // Asignar el código de la cita al objeto de registro de atención.
  }

  /**
   * Método para registrar la atención de la cita.
   * Realiza una petición al servicio para registrar la atención con los datos proporcionados.
   * Muestra una alerta de éxito o error según el resultado de la operación.
   */
  public atenderCita() {
    let codigo = this.tokenService.getCodigo(); // Obtener el código del médico logueado.
    // Asignar el código del médico al objeto de registro de atención.
    this.registroAtencionDTO.codigoMedico = codigo;

    this.medicoService.atenderCita(this.registroAtencionDTO).subscribe({
      next: data => {
        alert("Registro de Atención Exitoso"); // Alerta de éxito.
        console.log(data); // Mostrar respuesta en la consola (opcional).
      },
      error: error => {
        console.log(error); // Mostrar error en la consola (opcional).
      }
    });
  }
}
