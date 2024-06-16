import { Component } from '@angular/core';
import { DetalleAtencionMedicoDTO } from 'src/app/modelo/DetalleAtencionMedicoDTO'; // Importa el modelo para detalles de atención del médico
import { FiltroBusquedaDTO } from 'src/app/modelo/FiltroBusquedaDTO'; // Importa el modelo para el filtro de búsqueda
import { ItemCitaDTO } from 'src/app/modelo/ItemCitaDTO'; // Importa el modelo para los items de cita
import { Alerta } from 'src/app/modelo/alerta'; // Importa el modelo para las alertas
import { PacienteService } from 'src/app/servicios/paciente.service'; // Importa el servicio del paciente
import { TokenService } from 'src/app/servicios/token.service'; // Importa el servicio del token

@Component({
  selector: 'app-gestion-atenciones-paciente',
  templateUrl: './gestion-atenciones-paciente.component.html',
  styleUrls: ['./gestion-atenciones-paciente.component.css']
})
export class GestionAtencionesPacienteComponent {
  atenciones: DetalleAtencionMedicoDTO[]; // Array para almacenar las atenciones del paciente
  citasRealizadas: ItemCitaDTO[]; // Array para almacenar las citas realizadas por el paciente

  filtroBusquedaDTO: FiltroBusquedaDTO; // Objeto para el filtro de búsqueda
  filtroAtencion: DetalleAtencionMedicoDTO; // Objeto para el detalle de atención filtrado

  citasPorFecha: FiltroBusquedaDTO[]; // Array para almacenar citas filtradas por fecha
  citasPorCodigoMedico: FiltroBusquedaDTO[]; // Array para almacenar citas filtradas por código de médico

  alerta!: Alerta; // Objeto para mostrar alertas en la interfaz

  constructor(private tokenService: TokenService, private pacienteService: PacienteService) {
    // Inicialización de propiedades en el constructor
    this.atenciones = [];
    this.citasRealizadas = [];
    this.obtencionCitasRealizadas(); // Llama al método para obtener las citas realizadas al inicializar el componente

    this.verDetalleAtencion(); // Llama al método para ver el detalle de las atenciones

    this.filtroBusquedaDTO = new FiltroBusquedaDTO(); // Inicializa el objeto de filtro de búsqueda
    this.filtroAtencion = new DetalleAtencionMedicoDTO(); // Inicializa el objeto de detalle de atención filtrado

    this.citasPorFecha = []; // Inicializa el array para citas filtradas por fecha
    this.citasPorCodigoMedico = []; // Inicializa el array para citas filtradas por código de médico
  }

  /**
   * Método para ver el detalle de las atenciones realizadas por el paciente.
   * Utiliza las citas realizadas obtenidas para obtener el historial de atenciones por cada cita.
   */
  public verDetalleAtencion() {
    this.obtencionCitasRealizadas().then(() => {
      // Inicializa this.atenciones como un array vacío
      this.atenciones = [];

      // Realiza operaciones con las citas después de obtenerlas
      for (let i = 0; i < this.citasRealizadas.length; i++) {
        const cita = this.citasRealizadas[i].codigoCita;

        this.pacienteService.listarHistorialAtenciones(cita).subscribe({
          next: data => {
            // Agrega las atenciones al array existente en lugar de sobrescribirlo
            this.atenciones = this.atenciones.concat(data.respuesta);
            console.log(this.atenciones);
          },
          error: error => {
            console.log(error);
          }
        });
      }
    });
  }

  /**
   * Método para obtener las citas realizadas por el paciente.
   * Utiliza el servicio `PacienteService` para obtener las citas realizadas y las asigna a `citasRealizadas`.
   * @returns Promesa que se resuelve una vez que se han obtenido las citas realizadas.
   */
  public obtencionCitasRealizadas(): Promise<void> {

    return new Promise<void>((resolve, reject) => {

      let codigo = this.tokenService.getCodigo(); // Obtiene el código del paciente autenticado desde el servicio TokenService
      this.pacienteService.listarCitasRealizadas(codigo).subscribe({
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

  /**
   * Método para filtrar las citas por código de médico.
   * Utiliza el servicio `PacienteService` para obtener citas filtradas por el código de médico y las asigna a `citasPorCodigoMedico`.
   */
  public filtrarCitasPorMedico() {
    let codigoPaciente = this.tokenService.getCodigo();
    let codigoMedico = this.filtroBusquedaDTO.codigoMedico;

    if (codigoMedico == 0) {
      alert("Ingrese un valor diferente de 0");
    }

    console.log(codigoPaciente)

    this.pacienteService.filtrarAtencionesPorMedicox(codigoPaciente, codigoMedico).subscribe({
      next: data => {
        this.citasPorCodigoMedico = data.respuesta;
      },
      error: error => {
        this.alerta = { tipo: "danger", mensaje: error.error.respuesta }
        console.log(error);
      }
    });
  }

  /**
   * Método para manejar el cambio de fecha en el filtro y filtrar citas por fecha.
   * @param fecha La fecha seleccionada para filtrar citas.
   */
  public citasFecha(event: any) {
    // Obtener la fecha seleccionada del evento
    const fechaSeleccionada = event.target.value;
    
    // Filtrar citas por la fecha seleccionada
    this.filtrarCitasPorFecha(fechaSeleccionada);
  }

  /**
   * Método para filtrar las citas por fecha.
   * Utiliza el servicio `PacienteService` para obtener citas filtradas por la fecha y las asigna a `citasPorFecha`.
   * @param fecha La fecha seleccionada para filtrar citas.
   */
  public filtrarCitasPorFecha(fecha: string) {
    let codigoPaciente = this.tokenService.getCodigo();

    this.pacienteService.filtrarAtencionesPorFecha(codigoPaciente, fecha).subscribe({
      next: data => {
        this.citasPorFecha = data.respuesta;
      },
      error: error => {
        this.alerta = { tipo: "danger", mensaje: error.error.respuesta }
        console.log(error);
      }
    });

  }

  // Otros métodos no utilizados actualmente en la implementación proporcionada
  // public obtenerAtenciones() {
  //   this.citasRealizadas
  // }

}
