import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ItemCitaDTO } from 'src/app/modelo/ItemCitaDTO'; // Importación del modelo de datos para la cita
import { Alerta } from 'src/app/modelo/alerta'; // Importación del modelo para manejar alertas
import { MedicoService } from 'src/app/servicios/medico.service'; // Importación del servicio del médico
import { PacienteService } from 'src/app/servicios/paciente.service'; // Importación del servicio del paciente
import { TokenService } from 'src/app/servicios/token.service'; // Importación del servicio del token
import { DatePipe } from '@angular/common'; // Importación de DatePipe para manejar fechas

@Component({
  selector: 'app-gestion-atencion-cita',
  templateUrl: './gestion-atencion-cita.component.html',
  styleUrls: ['./gestion-atencion-cita.component.css'],
  providers: [DatePipe], // Se provee DatePipe para formatear fechas en el componente
})
export class GestionAtencionCitaComponent {

  citasPendientes: ItemCitaDTO[]; // Array para almacenar las citas pendientes obtenidas
  codigoCitaSeleccionada: number | undefined; // Código de la cita seleccionada
  alerta!: Alerta; // Variable para manejar y mostrar alertas en la interfaz

  constructor(private tokenService: TokenService, private pacienteService: PacienteService,
    private medicoService: MedicoService, private router: Router, private datePipe: DatePipe) {

    this.citasPendientes = []; // Inicialización del array de citas pendientes
    this.obtencionCitas(); // Llamada al método para obtener las citas pendientes al inicializar el componente
  }

  /**
   * Método para obtener las citas pendientes del médico autenticado.
   * Utiliza el servicio `MedicoService` para obtener las citas pendientes.
   */
  public obtencionCitas() {
    let codigo = this.tokenService.getCodigo(); // Obtiene el código del médico autenticado desde el servicio TokenService
    this.medicoService.listarCitasPendientes(codigo).subscribe({
      next: data => {
        this.citasPendientes = data.respuesta; // Asigna las citas pendientes obtenidas al array `citasPendientes`
      },
      error: error => {
        console.log(error); // Maneja errores imprimiendo en consola
      }
    });
  }

  /**
   * Método para seleccionar una cita pendiente.
   * @param codigoCita Código de la cita seleccionada
   */
  public seleccionar(codigoCita: number) {
    this.codigoCitaSeleccionada = codigoCita; // Asigna el código de la cita seleccionada a `codigoCitaSeleccionada`
  }

  /**
   * Método para atender una cita seleccionada.
   * Valida si la cita seleccionada es para el día actual antes de proceder a atenderla.
   * Navega a la página de atención de cita si la validación es exitosa.
   * Muestra una alerta si no se ha seleccionado ninguna cita o si la fecha de la cita no es hoy.
   */
  public atenderCita() {
    if (this.codigoCitaSeleccionada) { // Verifica si se ha seleccionado una cita
      const citaSeleccionada = this.citasPendientes.find(cita => cita.codigoCita === this.codigoCitaSeleccionada); // Busca la cita seleccionada en el array de citas pendientes

      if (citaSeleccionada) { // Verifica si se encontró la cita seleccionada
        // Obtiene la fecha actual en formato string
        const fechaActualString = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
        
        // Obtiene la fecha de la cita en formato string
        const fechaCitaString = this.datePipe.transform(citaSeleccionada.fecha, 'yyyy-MM-dd');

        if (fechaActualString === fechaCitaString) { // Verifica si la fecha de la cita es hoy
          // Navega a la página de atención de cita con el código de la cita seleccionada
          this.router.navigate(['/atender-cita', this.codigoCitaSeleccionada]);
        } else {
          // Muestra una alerta si la fecha de la cita no es hoy
          console.error('La fecha de la cita no es hoy.');
          this.alerta = { tipo: "danger", mensaje: "La fecha de la cita no es hoy" };
        }
      } else {
        // Muestra una alerta si la cita no se encontró en la lista de citas pendientes
        console.error('Cita no encontrada.');
        this.alerta = { tipo: "danger", mensaje: "Cita no encontrada" };
      }
    } else {
      // Muestra una alerta si no se ha seleccionado ninguna cita
      console.error('No se ha seleccionado ninguna cita.');
      this.alerta = { tipo: "danger", mensaje: "No se ha seleccionado ninguna cita" };
    }
  }

}
