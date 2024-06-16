import { Component } from '@angular/core';
import { DetalleAtencionMedicoDTO } from 'src/app/modelo/DetalleAtencionMedicoDTO'; // Importa el modelo para detalles de atención del médico
import { ItemCitaDTO } from 'src/app/modelo/ItemCitaDTO'; // Importa el modelo para los items de cita
import { MedicoService } from 'src/app/servicios/medico.service'; // Importa el servicio del médico
import { PacienteService } from 'src/app/servicios/paciente.service'; // Importa el servicio del paciente
import { TokenService } from 'src/app/servicios/token.service'; // Importa el servicio del token

@Component({
  selector: 'app-gestion-atenciones',
  templateUrl: './gestion-atenciones.component.html',
  styleUrls: ['./gestion-atenciones.component.css']
})
export class GestionAtencionesComponent {

  atenciones: DetalleAtencionMedicoDTO[]; // Array para almacenar las atenciones del médico
  citasRealizadas: ItemCitaDTO[]; // Array para almacenar las citas realizadas por el médico

  constructor(private tokenService: TokenService, private medicoService: MedicoService) {

    this.atenciones = []; // Inicializa el array de atenciones vacío

    this.citasRealizadas = []; // Inicializa el array de citas realizadas vacío
    this.obtencionCitasRealizadas(); // Llama al método para obtener las citas realizadas al inicializar el componente

    this.verDetalleAtencion(); // Llama al método para ver el detalle de las atenciones

    //console.log(this.citasRealizadas);
  }

  /**
   * Método para ver el detalle de las atenciones realizadas.
   * Utiliza las citas realizadas obtenidas para obtener el historial de atenciones por cada cita.
   */
  public verDetalleAtencion() {
    this.obtencionCitasRealizadas().then(() => {
      // Inicializa this.atenciones como un array vacío
      this.atenciones = [];
  
      // Realiza operaciones con las citas después de obtenerlas
      for (let i = 0; i < this.citasRealizadas.length; i++) {
        const cita = this.citasRealizadas[i].codigoCita;
  
        this.medicoService.listarHistorialAtenciones(cita).subscribe({
          next: data => {
            // Agrega las atenciones al array existente en lugar de sobrescribirlo
            this.atenciones = this.atenciones.concat(data.respuesta);
            console.log(this.atenciones); // Muestra las atenciones en la consola
          },
          error: error => {
            console.log(error); // Maneja errores imprimiendo en consola
          }
        });
      }
    });
  }

  /**
   * Método para obtener las citas realizadas por el médico.
   * Utiliza el servicio `MedicoService` para obtener las citas realizadas y las asigna a `citasRealizadas`.
   * @returns Promesa que se resuelve una vez que se han obtenido las citas realizadas.
   */
  public obtencionCitasRealizadas(): Promise<void> {

    return new Promise<void>((resolve, reject) => {

      let codigo = this.tokenService.getCodigo(); // Obtiene el código del médico autenticado desde el servicio TokenService
      this.medicoService.listarCitasRealizadas(codigo).subscribe({
        next: data => {
          this.citasRealizadas = data.respuesta; // Asigna las citas realizadas obtenidas al array `citasRealizadas`
          // console.log(this.citasRealizadas); // Imprime las citas realizadas
          resolve(); // Resuelve la promesa cuando se han obtenido las citas
        },
        error: error => {
          console.log(error); // Maneja errores imprimiendo en consola
          reject(); // Rechaza la promesa en caso de error
        }
      });
    });
  }

  // Otros métodos no utilizados actualmente en la implementación proporcionada
  // public obtenerAtenciones() {
  //   this.citasRealizadas
  // }

  // public seleccionar(codigoCita: number) {
  //   //this.codigoCitaSeleccionada = codigoCita;
  // }

}
