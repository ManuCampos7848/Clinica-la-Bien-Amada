import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DetalleCitaDTO } from 'src/app/modelo/DetalleCitaDTO'; // Importación del modelo para el detalle de la cita
import { PacienteService } from 'src/app/servicios/paciente.service'; // Importación del servicio del paciente
import { PqrsService } from 'src/app/servicios/pqrs.service'; // Importación del servicio de PQRS

@Component({
  selector: 'app-detalle-cita',
  templateUrl: './detalle-cita.component.html',
  styleUrls: ['./detalle-cita.component.css']
})
export class DetalleCitaComponent {

  detalleCita: DetalleCitaDTO | undefined; // Variable para almacenar el detalle de la cita
  codigoCita = 0; // Variable para almacenar el código de la cita, inicializada en 0

  constructor(private route: ActivatedRoute, private pqrsService: PqrsService,
    private pacienteService: PacienteService) {

    // Suscripción a los parámetros de la URL para obtener el código de la cita
    this.route.params.subscribe(params => {
      this.codigoCita = params['codigoCita']; // Asignación del código de la cita obtenido de los parámetros de la URL

      // Llamada al método para obtener y mostrar el detalle de la cita
      this.verDetalleCita();
    });
  }

  /**
   * Método para obtener el detalle de la cita específica.
   */
  public verDetalleCita() {
    // Llamada al servicio para obtener el detalle de la cita según el código de cita
    this.pacienteService.verDetalleCita(this.codigoCita).subscribe({
      next: data => {
        this.detalleCita = data.respuesta; // Asignación del detalle de la cita obtenido a la variable correspondiente
      },
      error: error => {
        console.log(error); // Manejo de errores: imprimir el error en la consola (opcional)
      }
    });
  }

}
