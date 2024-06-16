import { Component } from '@angular/core';
import { FiltroBusquedaDTO } from 'src/app/modelo/FiltroBusquedaDTO';
import { ItemCitaDTO } from 'src/app/modelo/ItemCitaDTO';
import { Alerta } from 'src/app/modelo/alerta';
import { CitaService } from 'src/app/servicios/cita.service';
import { PacienteService } from 'src/app/servicios/paciente.service';
import { TokenService } from 'src/app/servicios/token.service';

@Component({
  selector: 'app-gestion-citas-paciente',
  templateUrl: './gestion-citas-paciente.component.html',
  styleUrls: ['./gestion-citas-paciente.component.css']
})
export class GestionCitasPacienteComponent {

  citas: ItemCitaDTO[]; // Arreglo que almacenará las citas del paciente.

  citasPorFecha: FiltroBusquedaDTO[]; // Arreglo que almacenará las citas filtradas por fecha.
  citasPorCodigoMedico: FiltroBusquedaDTO[]; // Arreglo que almacenará las citas filtradas por código de médico.

  fecha: string = ""; // Variable para almacenar la fecha seleccionada por el usuario.
  alerta!: Alerta; // Objeto para manejar mensajes de alerta.

  filtroBusquedaDTO: FiltroBusquedaDTO; // Objeto utilizado para enviar filtros de búsqueda al servicio.

  constructor(private tokenService: TokenService, private pacienteService: PacienteService) {
    // Inicialización del componente.

    this.filtroBusquedaDTO = new FiltroBusquedaDTO(); // Inicialización del objeto de filtro.

    this.citas = []; // Inicialización del arreglo de citas.
    this.obtencionCitas(); // Llamada al método para obtener todas las citas del paciente al cargar el componente.

    this.citasPorFecha = []; // Inicialización del arreglo de citas filtradas por fecha.
    this.citasPorCodigoMedico = []; // Inicialización del arreglo de citas filtradas por código de médico.
  }

  /**
   * Método para filtrar citas por el código de un médico.
   * Realiza una petición al servicio para obtener las citas asociadas al paciente y al médico especificado.
   * Muestra las citas filtradas en la tabla correspondiente.
   */
  public filtrarCitasPorMedico() {
    let codigoPaciente = this.tokenService.getCodigo(); // Obtener el código del paciente autenticado.
    let codigoMedico = this.filtroBusquedaDTO.codigoMedico; // Obtener el código del médico ingresado en el formulario.

    if (codigoMedico == 0) {
      alert("Ingrese un valor diferente de 0");
    }

    this.pacienteService.filtrarCitasPorMedico(codigoPaciente, codigoMedico).subscribe({
      next: data => {
        this.citasPorCodigoMedico = data.respuesta; // Almacenar las citas filtradas por médico en el arreglo correspondiente.
      },
      error: error => {
        this.alerta = { tipo: "danger", mensaje: error.error.respuesta }; // Manejo de errores y muestra de mensaje de alerta.
        console.log(error);
      }
    });
  }
  //______________________________________________________________________________________

  /**
   * Método que se ejecuta al cambiar la fecha seleccionada por el usuario.
   * Filtra las citas por la fecha especificada y muestra los resultados en la tabla correspondiente.
   * @param event Objeto que contiene la fecha seleccionada por el usuario.
   */
  public citasFecha(event: any) {
    this.filtrarCitasPorFecha(event.target.value);
  }
  //______________________________________________________________________________________

  /**
   * Método para filtrar citas por fecha.
   * Realiza una petición al servicio para obtener las citas del paciente en la fecha especificada.
   * Muestra las citas filtradas en la tabla correspondiente.
   * @param fecha Fecha en formato string para filtrar las citas.
   */
  public filtrarCitasPorFecha(fecha: string) {
    let codigoPaciente = this.tokenService.getCodigo(); // Obtener el código del paciente autenticado.

    this.pacienteService.filtrarCitasPorFecha(codigoPaciente, fecha).subscribe({
      next: data => {
        this.citasPorFecha = data.respuesta; // Almacenar las citas filtradas por fecha en el arreglo correspondiente.
      },
      error: error => {
        this.alerta = { tipo: "danger", mensaje: error.error.respuesta }; // Manejo de errores y muestra de mensaje de alerta.
        console.log(error);
      }
    });
  }
  //______________________________________________________________________________________

  /**
   * Método para obtener todas las citas del paciente.
   * Realiza una petición al servicio para obtener todas las citas registradas para el paciente autenticado.
   * Almacena las citas en el arreglo correspondiente para mostrarlas en la tabla principal.
   */
  public obtencionCitas() {
    let codigo = this.tokenService.getCodigo(); // Obtener el código del paciente autenticado.

    this.pacienteService.listarCitasPaciente(codigo).subscribe({
      next: data => {
        this.citas = data.respuesta; // Almacenar todas las citas del paciente en el arreglo correspondiente.
      },
      error: error => {
        console.log(error); // Manejo de errores.
      }
    });
  }
  //______________________________________________________________________________________

}
