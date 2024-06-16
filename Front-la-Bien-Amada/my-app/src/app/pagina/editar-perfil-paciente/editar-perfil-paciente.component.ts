import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { DetallePacienteDTO } from 'src/app/modelo/DetallePacienteDTO'; // Importación del modelo de detalle del paciente
import { Alerta } from 'src/app/modelo/alerta'; // Importación del modelo para manejar alertas
import { AuthService } from 'src/app/servicios/auth.service'; // Importación del servicio de autenticación
import { ClinicaService } from 'src/app/servicios/clinica.service'; // Importación del servicio de la clínica
import { ImagenService } from 'src/app/servicios/imagen.service'; // Importación del servicio de imágenes
import { PacienteService } from 'src/app/servicios/paciente.service'; // Importación del servicio del paciente
import { TokenService } from 'src/app/servicios/token.service'; // Importación del servicio del token

@Component({
  selector: 'app-editar-perfil-paciente',
  templateUrl: './editar-perfil-paciente.component.html',
  styleUrls: ['./editar-perfil-paciente.component.css']
})
export class EditarPerfilPacienteComponent {

  detallePacienteDTO: DetallePacienteDTO; // Objeto para almacenar los detalles del paciente
  archivos!: FileList; // Lista de archivos seleccionados para subir (imagen de perfil)
  ciudades: string[]; // Array para almacenar las ciudades disponibles
  alerta!: Alerta; // Variable para manejar y mostrar alertas en la interfaz
  eps: string[]; // Array para almacenar las EPS disponibles
  tipoSangre: string[]; // Array para almacenar los tipos de sangre disponibles

  constructor(private authService: AuthService,
    private clinicaService: ClinicaService, private imagenService: ImagenService,
    private pacienteService: PacienteService, private tokenService: TokenService,
    private router: Router) {

    this.detallePacienteDTO = new DetallePacienteDTO(); // Inicialización del objeto para almacenar detalles del paciente

    this.ciudades = []; // Inicialización del array de ciudades
    this.cargarCiudades(); // Llamada al método para cargar las ciudades disponibles

    this.eps = []; // Inicialización del array de EPS
    this.cargarEPS(); // Llamada al método para cargar las EPS disponibles

    this.tipoSangre = []; // Inicialización del array de tipos de sangre
    this.cargarTipoSangre(); // Llamada al método para cargar los tipos de sangre disponibles

    this.detallePacienteDTO.codigo = tokenService.getCodigo(); // Asignación del código del paciente actualmente logueado
  }

  /**
   * Método para editar el perfil del paciente.
   * Realiza la validación de los campos obligatorios antes de permitir la edición.
   * Llama al servicio para actualizar los datos del paciente y subir la imagen de perfil si es necesario.
   */
  public editarPerfil() {
    // Verifica si se ha seleccionado una imagen de perfil
    if (this.detallePacienteDTO.urlFoto.length !== 0) {
      // Llama al servicio para editar el perfil del paciente
      this.pacienteService.editarPerfil(this.detallePacienteDTO).subscribe({
        next: data => {
          // Sube la imagen de perfil
          this.subirImagen();
          alert("Edición de perfil exitoso"); // Muestra un mensaje de éxito
          console.log(data); // Imprime en consola la respuesta obtenida del servicio
          // Redirecciona a la página de detalle del paciente con su código
          this.router.navigate(['/detalle-paciente', this.detallePacienteDTO.codigo]);
        },
        error: error => {
          alert("Asegúrate de llenar todos los campos primero"); // Muestra un mensaje de error
          this.alerta = { tipo: "danger", mensaje: error.error.respuesta }; // Asigna una alerta de tipo error
          console.log(error); // Imprime en consola el error obtenido del servicio
        }
      });
    } else {
      alert("Asegúrate de llenar todos los campos y subir una imagen"); // Muestra un mensaje de advertencia
      console.log("Debe subir una imagen"); // Imprime en consola un mensaje informativo
    }
  }

  /**
   * Método para obtener la fecha máxima permitida (hoy) en formato YYYY-MM-DD.
   * Utilizado en la interfaz para establecer la fecha máxima en los campos de fecha.
   */
  getMaxDate(): string {
    const today = new Date(); // Obtiene la fecha actual
    const year = today.getFullYear(); // Obtiene el año actual
    const month = (today.getMonth() + 1).toString().padStart(2, '0'); // Obtiene el mes actual y lo formatea con ceros a la izquierda si es necesario
    const day = today.getDate().toString().padStart(2, '0'); // Obtiene el día actual y lo formatea con ceros a la izquierda si es necesario
    
    return `${year}-${month}-${day}`; // Retorna la fecha en formato YYYY-MM-DD
  }

  /**
   * Método privado para cargar las ciudades disponibles.
   * Llama al servicio `ClinicaService` para obtener la lista de ciudades.
   */
  private cargarCiudades() {
    this.clinicaService.listarCiudades().subscribe({
      next: data => {
        this.ciudades = data.respuesta; // Asigna las ciudades obtenidas al array `ciudades`
      },
      error: error => {
        console.log(error); // Maneja errores imprimiendo en consola
      }
    });
  }

  /**
   * Método privado para cargar las EPS disponibles.
   * Llama al servicio `ClinicaService` para obtener la lista de EPS.
   */
  private cargarEPS() {
    this.clinicaService.listarEPS().subscribe({
      next: data => {
        this.eps = data.respuesta; // Asigna las EPS obtenidas al array `eps`
      },
      error: error => {
        console.log(error); // Maneja errores imprimiendo en consola
      }
    });
  }

  /**
   * Método privado para cargar los tipos de sangre disponibles.
   * Llama al servicio `ClinicaService` para obtener la lista de tipos de sangre.
   */
  private cargarTipoSangre() {
    this.clinicaService.listarTipoSangre().subscribe({
      next: data => {
        this.tipoSangre = data.respuesta; // Asigna los tipos de sangre obtenidos al array `tipoSangre`
      },
      error: error => {
        console.log(error); // Maneja errores imprimiendo en consola
      }
    });
  }

  /**
   * Método para subir una imagen de perfil seleccionada por el usuario.
   * Utiliza el servicio `ImagenService` para subir la imagen al servidor.
   */
  public subirImagen() {
    if (this.archivos != null && this.archivos.length > 0) { // Verifica si hay archivos seleccionados
      const formData = new FormData(); // Crea un objeto FormData para enviar la imagen
      formData.append('file', this.archivos[0]); // Agrega el archivo seleccionado al FormData
      
      // Llama al servicio `ImagenService` para subir la imagen
      this.imagenService.subir(formData).subscribe({
        next: (data: { respuesta: { url: string; }; }) => {
          this.detallePacienteDTO.urlFoto = data.respuesta.url; // Asigna la URL de la imagen subida al detalle del paciente
        },
        error: (error: { error: any; }) => {
          this.alerta = { mensaje: error.error, tipo: "danger" }; // Maneja errores asignando una alerta de tipo error
        }
      });
    } else {
      this.alerta = { mensaje: 'Debe seleccionar una imagen y subirla', tipo: "danger" }; // Muestra un mensaje de advertencia si no se seleccionó ninguna imagen
    }
  }

  /**
   * Método para manejar el cambio en la selección de archivos (imagen de perfil).
   * Actualiza `detallePacienteDTO.urlFoto` con el nombre del archivo seleccionado.
   * Actualiza `archivos` con la lista de archivos seleccionados.
   * @param event Evento generado por el cambio en la selección de archivos.
   */
  public onFileChange(event: any) {
    if (event.target.files.length > 0) {
      this.detallePacienteDTO.urlFoto = event.target.files[0].name; // Asigna el nombre del archivo seleccionado a `detallePacienteDTO.urlFoto`
      this.archivos = event.target.files; // Asigna la lista de archivos seleccionados a `archivos`
    }
  }

  /**
   * Metodo que elimina la cuenta del paciente.
   */
  public eliminarCuenta() {
    const confirmacion = confirm('¿Estás seguro de que quieres eliminar tu cuenta?');
  
    if (confirmacion) {
      let codigo = this.tokenService.getCodigo();
  
      this.pacienteService.eliminarCuenta(codigo).subscribe({
        next: data => {
          alert('Cuenta eliminada con éxito');
          this.tokenService.logout();
        },
        error: error => {
          console.log(error);
        }
      });
    } else {
      // El usuario ha cancelado la operación
      console.log('Operación de eliminación de cuenta cancelada');
    }
  }
  
}
