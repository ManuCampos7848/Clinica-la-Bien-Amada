import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { DetalleCitaDTO } from 'src/app/modelo/DetalleCitaDTO';
import { ItemCitaDTO } from 'src/app/modelo/ItemCitaDTO';
import { RegistroPQRSDTO } from 'src/app/modelo/RegistroPQRSDTO';
import { Alerta } from 'src/app/modelo/alerta';
import { PacienteService } from 'src/app/servicios/paciente.service';
import { TokenService } from 'src/app/servicios/token.service';

@Component({
  selector: 'app-crear-pqrs',
  templateUrl: './crear-pqrs.component.html',
  styleUrls: ['./crear-pqrs.component.css']
})
export class CrearPqrsComponent {

  registroPQRSDTO: RegistroPQRSDTO; // Objeto para almacenar los datos del registro de PQRS.
  alerta!: Alerta; // Objeto para manejar mensajes de alerta.

  citasPaciente: ItemCitaDTO[]; // Lista de citas del paciente.
  detalleCita: DetalleCitaDTO | undefined; // Detalle de la cita seleccionada.

  constructor(private pacienteService: PacienteService,
              private tokenService: TokenService, private router: Router) {
    this.registroPQRSDTO = new RegistroPQRSDTO(); // Inicialización del objeto de registro de PQRS.

    this.citasPaciente = []; 
    this.cargarDatos(); 
    // Inicialización de la lista de citas del paciente.
   // Cargar las citas del paciente al inicializar el componente.
  }

  /**
   * Método para crear una nueva PQRS.
   * Realiza una petición al servicio para registrar la PQRS con los datos proporcionados.
   * Muestra una alerta de éxito o error según el resultado de la operación.
   * Navega a la página de gestión de PQRS después de un retraso de 2 segundos.
   */
  public crearPqrs() {
    const fechaActual = new Date(); // Obtener la fecha actual.
    let codigo = this.tokenService.getCodigo(); // Obtener el código del paciente logueado.

    // Asignar los datos necesarios para la creación de la PQRS.
    this.registroPQRSDTO.estadoPQRS = 'NUEVO';
    this.registroPQRSDTO.fechaCreacion = fechaActual;
    this.registroPQRSDTO.codigoPaciente = codigo;

    // Realizar la petición para crear la PQRS.
    this.pacienteService.crearPQRS(this.registroPQRSDTO).subscribe({
      next: data => {
        alert("PQRS registrada con éxito"); // Alerta de éxito.
        this.alerta = { tipo: "success", mensaje: data.respuesta }; // Asignar mensaje de éxito.
        // Agregar un retraso de 2 segundos antes de navegar a la gestión de PQRS.
        setTimeout(() => {
          this.router.navigate(['/gestion-pqrs']);
        }, 2000);
      },
      error: data => {
        this.alerta = { tipo: "danger", mensaje: data.error.respuesta }; // Asignar mensaje de error.
      }
    });
  }

  /**
   * Método para cargar las citas realizadas por el paciente.
   * Realiza una petición al servicio para obtener las citas del paciente logueado.
   */
  public cargarDatos() {
    let codigo = this.tokenService.getCodigo(); // Obtener el código del paciente logueado.
    // Realizar la petición para obtener las citas del paciente.
    this.pacienteService.listarCitasRealizadas(codigo).subscribe({
      next: data => {
        this.citasPaciente = data.respuesta; // Asignar las citas obtenidas.
      },
      error: error => {
        console.log(error); // Manejar el error en la consola (opcional).
      }
    });
  }

  /**
   * Método para seleccionar una cita y obtener su detalle.
   * Realiza una petición al servicio para obtener el detalle de la cita seleccionada.
   * @param codigoCita Código de la cita seleccionada.
   */
  public seleccionar(codigoCita: number) {
    this.registroPQRSDTO.codigoCita = codigoCita; // Asignar el código de la cita al objeto de registro de PQRS.
    // Realizar la petición para obtener el detalle de la cita seleccionada.
    this.pacienteService.verDetalleCita(this.registroPQRSDTO.codigoCita).subscribe({
      next: data => {
        this.detalleCita = data.respuesta; // Asignar el detalle de la cita obtenido.
      },
      error: error => {
        console.log(error); // Manejar el error en la consola (opcional).
      }
    });
  }
}
