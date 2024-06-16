import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Location } from '@angular/common';
import { HorarioDTO } from 'src/app/modelo/HorarioDTO';
import { ItemMedicoDTO } from 'src/app/modelo/ItemMedicoDTO';
import { RegistroCitaDTO } from 'src/app/modelo/RegistroCitaDTO';
import { Alerta } from 'src/app/modelo/alerta';
import { ChangeDetectorRef } from '@angular/core';
import { MensajeDTO } from 'src/app/modelo/mensaje-dto';
import { CitaService } from 'src/app/servicios/cita.service';
import { ClinicaService } from 'src/app/servicios/clinica.service';
import { PacienteService } from 'src/app/servicios/paciente.service';
import { TokenService } from 'src/app/servicios/token.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-agendar-cita',
  templateUrl: './agendar-cita.component.html',
  styleUrls: ['./agendar-cita.component.css']
})
export class AgendarCitaComponent {

  alerta!: Alerta; // Objeto para manejar mensajes de alerta.

  registroCitaDTO: RegistroCitaDTO; // Objeto para almacenar los datos de la cita a registrar.
  especialidades: String[]; // Arreglo para almacenar las especialidades médicas disponibles.
  medicoPorEspecialidad: ItemMedicoDTO[]; // Arreglo para almacenar los médicos disponibles según la especialidad seleccionada.
  horarioMedico: HorarioDTO[]; // Arreglo para almacenar el horario disponible del médico seleccionado.

  especialidad: string = ''; // Variable para almacenar la especialidad seleccionada por el usuario.

  constructor(private clinicaService: ClinicaService, private citaService: CitaService,
    private pacienteService: PacienteService, private tokenService: TokenService,
    private cdr: ChangeDetectorRef, private router: Router,
    private location: Location) {
    
    this.registroCitaDTO = new RegistroCitaDTO(); // Inicialización del objeto de registro de cita.

    this.especialidades = []; // Inicialización del arreglo de especialidades.
    this.cargarEspecialidades(); // Cargar las especialidades disponibles al iniciar el componente.

    this.medicoPorEspecialidad = []; // Inicialización del arreglo de médicos por especialidad.

    this.horarioMedico = []; // Inicialización del arreglo de horarios del médico.

    this.cargarDatos(); // Cargar datos iniciales necesarios para agendar la cita.
  }

  /**
   * Método para cargar los datos iniciales para agendar una cita.
   * Obtiene y asigna el código del paciente y establece valores predeterminados para la sede y estado de la cita.
   */
  public cargarDatos() {
    let codigo = this.tokenService.getCodigo();
    this.registroCitaDTO.codigoPaciente = codigo;

    this.registroCitaDTO.sede = 'SLYTHERIN'; // Valor predeterminado de la sede para la cita.
    this.registroCitaDTO.estadoCita = 'PROGRAMADA'; // Estado predeterminado de la cita.
  }

  /**
   * Método para obtener la fecha mínima permitida para seleccionar en el calendario.
   * Retorna la fecha actual en formato YYYY-MM-DD.
   */
  getMinDate(): string {
    const today = new Date();
    const year = today.getFullYear();
    const month = (today.getMonth() + 1).toString().padStart(2, '0');
    const day = today.getDate().toString().padStart(2, '0');

    return `${year}-${month}-${day}`;
  }

  /**
   * Método privado para cargar las especialidades médicas disponibles.
   * Realiza una petición al servicio para obtener las especialidades y las asigna al arreglo correspondiente.
   */
  private cargarEspecialidades() {
    this.clinicaService.listarEspecialidades().subscribe({
      next: data => {
        this.especialidades = data.respuesta;
      },
      error: error => {
        console.log(error); // Manejo de errores.
      }
    });
  }

  /**
   * Método privado para listar los médicos disponibles por una especialidad seleccionada.
   * Realiza una petición al servicio y asigna los médicos recuperados al arreglo correspondiente.
   * @param especialidad Especialidad seleccionada para filtrar los médicos.
   */
  private listarMedicoPorEspecialidad(especialidad: string) {
    this.clinicaService.listarMedicoPorEspecialidad(especialidad).subscribe({
      next: data => {
        this.medicoPorEspecialidad = data.respuesta;
      },
      error: error => {
        console.log(error); // Manejo de errores.
      }
    });
  }

  /**
   * Método para seleccionar un médico para la cita.
   * Asigna el código del médico seleccionado al objeto de registro de cita.
   * @param codigoMedico Código del médico seleccionado.
   */
  public seleccionar(codigoMedico: number) {
    this.registroCitaDTO.codigoMedico = codigoMedico;
  }

  /**
   * Método para agendar una nueva cita.
   * Realiza una petición al servicio para registrar la cita con los datos proporcionados.
   * Navega a la página de gestión de citas del paciente después de un registro exitoso.
   */
  public agendarCita() {
    console.log('Datos de la cita:', this.registroCitaDTO); // Verifica los datos antes de enviar
    this.pacienteService.agendarCita(this.registroCitaDTO).subscribe({
      next: data => {
        alert('Registro Exitoso');
        console.log(data);
        this.router.navigate(['/gestion-citas-paciente']);
      },
      error: error => {
        console.log(error);
        this.alerta = { tipo: 'danger', mensaje: error.error.respuesta };
      }
    });
  }
  

  /**
   * Método para listar los médicos disponibles al seleccionar una especialidad en el formulario.
   * Realiza una petición al servicio y asigna los médicos recuperados al arreglo correspondiente.
   * @param event Objeto que contiene el evento del cambio de especialidad seleccionada.
   */
  public listarMedicos(event: any) {
    const especialidadSeleccionada = event.target.value;
    this.listarMedicoPorEspecialidad(especialidadSeleccionada); // Llamar al método privado para listar médicos por especialidad.
  }
}
