import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DetalleAtencionMedicoDTO } from 'src/app/modelo/DetalleAtencionMedicoDTO'; // Importación del modelo para el detalle de la atención médica
import { PacienteService } from 'src/app/servicios/paciente.service'; // Importación del servicio del paciente
import { PqrsService } from 'src/app/servicios/pqrs.service'; // Importación del servicio de PQRS

@Component({
  selector: 'app-detalle-atencion-paciente',
  templateUrl: './detalle-atencion-paciente.component.html',
  styleUrls: ['./detalle-atencion-paciente.component.css']
})
export class DetalleAtencionPacienteComponent {

  detalleAtencion: DetalleAtencionMedicoDTO | undefined; // Variable para almacenar el detalle de la atención médica
  codigoCita = 0; // Variable para almacenar el código de la cita, inicializada en 0

  constructor(private route: ActivatedRoute, private pqrsService: PqrsService,
    private pacienteService: PacienteService) {

    // Suscripción a los parámetros de la URL para obtener el código de la cita
    this.route.params.subscribe(params => {
      this.codigoCita = params['codigoCita']; // Asignación del código de la cita obtenido de los parámetros de la URL

      console.log(this.codigoCita); // Impresión del código de la cita en la consola (opcional)

      // Llamada al método para obtener y mostrar el detalle de la atención médica
      this.verDetalleAtencion();
    });
  }

  /**
   * Método para obtener el detalle de la atención médica de la cita especificada.
   */
  public verDetalleAtencion() {
    // Llamada al servicio para obtener el detalle de la atención médica según el código de cita
    this.pacienteService.verDetalleAtencion(this.codigoCita).subscribe({
      next: data => {
        this.detalleAtencion = data.respuesta; // Asignación del detalle de atención obtenido a la variable correspondiente
      },
      error: error => {
        console.log(error); // Manejo de errores: imprimir el error en la consola (opcional)
      }
    });
  }
  
}
