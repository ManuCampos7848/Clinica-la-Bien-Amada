import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DetallePacienteDTO } from 'src/app/modelo/DetallePacienteDTO'; // Importación del modelo para el detalle del paciente
import { PacienteService } from 'src/app/servicios/paciente.service'; // Importación del servicio del paciente
import { TokenService } from 'src/app/servicios/token.service'; // Importación del servicio del token

@Component({
  selector: 'app-detalle-paciente',
  templateUrl: './detalle-paciente.component.html',
  styleUrls: ['./detalle-paciente.component.css']
})
export class DetallePacienteComponent {

  detallePacienteDTO: DetallePacienteDTO | undefined; // Variable para almacenar el detalle del paciente
  codigoPaciente: number = 0; // Variable para almacenar el código del paciente, inicializada en 0

  constructor(private pacienteService: PacienteService,
     private tokenService: TokenService, private route: ActivatedRoute){

      // Suscripción a los parámetros de la URL para obtener el código del paciente
      this.route.params.subscribe(params => {
        this.codigoPaciente = params['codigo']; // Asignación del código del paciente obtenido de los parámetros de la URL
  
        // Llamada al método para obtener y mostrar el detalle del paciente
        this.verDetallePaciente();
      });
  }

  /**
   * Método para obtener el detalle del paciente específico.
   */
  public verDetallePaciente() {
    let codigo = this.tokenService.getCodigo(); // Obtención del código del paciente a través del servicio TokenService

    // Llamada al servicio para obtener el detalle del paciente según su código
    this.pacienteService.verDetallePaciente(codigo).subscribe({
      next: data => {
        this.detallePacienteDTO = data.respuesta; // Asignación del detalle del paciente obtenido a la variable correspondiente
      },
      error: error => {
        console.log(error); // Manejo de errores: imprimir el error en la consola (opcional)
      }
    });
  }
}
