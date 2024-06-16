import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Alerta } from 'src/app/modelo/alerta';
import { RegistroPacienteDTO } from 'src/app/modelo/registro-paciente-dto';
import { AuthService } from 'src/app/servicios/auth.service';
import { ClinicaService } from 'src/app/servicios/clinica.service';
import { ImagenService } from 'src/app/servicios/imagen.service';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent {
  // Alerta para mostrar mensajes al usuario
  alerta!: Alerta;

  // Instancia del objeto de datos de registro de paciente
  registroPacienteDTO: RegistroPacienteDTO;
  // Listas para almacenar las ciudades, EPS y tipos de sangre disponibles
  ciudades: string[];
  eps: string[];
  tipoSangre: string[];

  constructor(private authService: AuthService,
              private clinicaService: ClinicaService, private imagenService: ImagenService,
              private router: Router) {

    // Inicializa el objeto de datos de registro de paciente
    this.registroPacienteDTO = new RegistroPacienteDTO();

    // Inicializa las listas de ciudades, EPS y tipos de sangre
    this.ciudades = [];
    this.cargarCiudades();
    console.log(this.ciudades);

    this.eps = [];
    this.cargarEPS();

    this.tipoSangre = [];
    this.cargarTipoSangre();
  }

  // Método para cargar las ciudades disponibles
  private cargarCiudades() {
    this.clinicaService.listarCiudades().subscribe({
      next: data => {
        this.ciudades = data.respuesta;
      },
      error: error => {
        console.log(error);
      }
    });
  }

  // Método para cargar las EPS disponibles
  private cargarEPS() {
    this.clinicaService.listarEPS().subscribe({
      next: data => {
        this.eps = data.respuesta;
      },
      error: error => {
        console.log(error);
      }
    });
  }

  // Método para cargar los tipos de sangre disponibles
  private cargarTipoSangre() {
    this.clinicaService.listarTipoSangre().subscribe({
      next: data => {
        this.tipoSangre = data.respuesta;
      },
      error: error => {
        console.log(error);
      }
    });
  }

  archivos!: FileList;

  // Método para manejar el registro del paciente
  public registrar() {
    if (this.registroPacienteDTO.urlFoto.length != 0) {
      this.authService.registrarPaciente(this.registroPacienteDTO).subscribe({
        next: data => {
          alert("Registro Exitoso");
          console.log(data);
          this.router.navigate(['/login']);
        },
        error: error => {
          alert("Asegúrate de llenar todos los campos primero");
          console.log(error);
        }
      });
    } else {
      alert("Asegúrate de llenar todos los campos y subir una imagen");
      console.log("Debe subir una imagen");
    }
  }

  // Método para obtener la fecha máxima permitida para el campo de fecha de nacimiento
  getMaxDate(): string {
    const today = new Date();
    const year = today.getFullYear();
    const month = (today.getMonth() + 1).toString().padStart(2, '0');
    const day = today.getDate().toString().padStart(2, '0');
    
    return `${year}-${month}-${day}`;
  }

  // Método para verificar si las contraseñas ingresadas son iguales
  public sonIguales(): boolean {
    return this.registroPacienteDTO.password == this.registroPacienteDTO.confirmaPassword;
  }

  // Método para manejar el cambio de archivo (imagen)
  public onFileChange(event: any) {
    if (event.target.files.length > 0) {
      this.registroPacienteDTO.urlFoto = event.target.files[0].name;
      this.archivos = event.target.files;
    }
  }

  // Método para subir la imagen seleccionada
  public subirImagen() {
    if (this.archivos != null && this.archivos.length > 0) {
      const formData = new FormData();
      formData.append('file', this.archivos[0]);
      this.imagenService.subir(formData).subscribe({
        next: data => {  
          this.registroPacienteDTO.urlFoto = data.respuesta.url;
        },
        error: error => {
          this.alerta = { mensaje: error.error, tipo: "danger" };
        }
      });
    } else {
      this.alerta = { mensaje: 'Debe seleccionar una imagen y subirla', tipo: "danger" };
    }
  }
}
