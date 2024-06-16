import { Component } from '@angular/core';
import { DiaLibreDTO } from 'src/app/modelo/DiaLibreDTO';
import { Alerta } from 'src/app/modelo/alerta';
import { MedicoService } from 'src/app/servicios/medico.service';
import { TokenService } from 'src/app/servicios/token.service';

@Component({
  selector: 'app-agendar-dia-libre',
  templateUrl: './agendar-dia-libre.component.html',
  styleUrls: ['./agendar-dia-libre.component.css']
})
export class AgendarDiaLibreComponent {

  diaLibreDTO: DiaLibreDTO; // Objeto para almacenar los datos del día libre a agendar.
  alerta!: Alerta; // Objeto para manejar mensajes de alerta.

  constructor(private tokenService: TokenService, private medicoService: MedicoService) {
    this.diaLibreDTO = new DiaLibreDTO(); // Inicialización del objeto de día libre.
  }

  /**
   * Método para agendar un día libre para un médico.
   * Realiza una petición al servicio para agendar el día libre con los datos proporcionados.
   * Muestra una alerta de éxito o error según el resultado de la operación.
   */
  public agendarDiaLibre() {
    let codigo = this.tokenService.getCodigo(); // Obtener el código del médico logueado.
    this.diaLibreDTO.codigoMedico = codigo; // Asignar el código del médico al objeto de día libre.

    this.medicoService.agendarDiaLibre(this.diaLibreDTO).subscribe({
      next: data => {
        alert("Día agendado con éxito"); // Alerta de éxito.
        this.alerta = { tipo: "success", mensaje: data.respuesta }; // Asignar mensaje de éxito a la alerta.
      },
      error: error => {
        this.alerta = { tipo: "danger", mensaje: error.error.respuesta }; // Asignar mensaje de error a la alerta.
      }
    });
  }
}
