import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { DetallePQRSDTO } from 'src/app/modelo/DetallePQRSDTO'; // Importación del modelo para el detalle del PQR
import { RegistroRespuestaDTO } from 'src/app/modelo/RegistroRespuestaDTO'; // Importación del modelo para registrar una respuesta
import { RespuestaDTO } from 'src/app/modelo/RespuestaDTO'; // Importación del modelo para la respuesta de un PQR
import { Alerta } from 'src/app/modelo/alerta'; // Importación del modelo para manejar alertas
import { PacienteService } from 'src/app/servicios/paciente.service'; // Importación del servicio del paciente
import { PqrsService } from 'src/app/servicios/pqrs.service'; // Importación del servicio para PQRs
import { TokenService } from 'src/app/servicios/token.service'; // Importación del servicio del token

@Component({
  selector: 'app-detalle-pqrs',
  templateUrl: './detalle-pqrs.component.html',
  styleUrls: ['./detalle-pqrs.component.css']
})
export class DetallePqrsComponent {

  mensajesPQRS: string = ''; // Cadena para almacenar y mostrar los mensajes del PQR

  codigoPqrs: number = 0; // Código del PQR actualmente consultado
  pqrs: DetallePQRSDTO | undefined; // Detalle completo del PQR consultado

  registroRespuestaDTO: RegistroRespuestaDTO; // Objeto para almacenar los datos de la respuesta
  respuestaDTO: RespuestaDTO[]; // Array para almacenar las respuestas relacionadas al PQR

  alerta!: Alerta; // Variable para manejar y mostrar alertas en la interfaz

  constructor(private route: ActivatedRoute, private pqrsService: PqrsService,
    private pacienteService: PacienteService,
    private tokenService: TokenService, private router: Router,
    private location: Location) {

    this.registroRespuestaDTO = new RegistroRespuestaDTO(); // Inicialización del objeto para registrar una respuesta

    this.respuestaDTO = []; // Inicialización del array de respuestas

    // Suscripción a los parámetros de la URL para obtener el código del PQR
    this.route.params.subscribe(params => {
      this.codigoPqrs = params['codigo']; // Asignación del código del PQR obtenido de los parámetros de la URL

      // Llamada al método para obtener y mostrar los detalles del PQR
      let pqrsConsultado = this.obtenerPqrs();

      // Si se obtiene el PQR correctamente, asignarlo a la variable pqrs
      if (pqrsConsultado !== undefined) {
        this.pqrs = pqrsConsultado;
      }
    });
  }

  /**
   * Método para obtener y mostrar los detalles del PQR actualmente consultado.
   */
  public obtenerPqrs() {
    // Llamada al servicio para obtener los detalles del PQR según su código
    this.pacienteService.verDetallePQRS(this.codigoPqrs).subscribe({
      next: data => {
        this.pqrs = data.respuesta; // Asignación del detalle del PQR obtenido a la variable pqrs
        this.obtenerMensajes(); // Llamada al método para obtener los mensajes relacionados al PQR
      },
      error: error => {
        console.log(error); // Manejo de errores: imprimir el error en la consola (opcional)
      }
    });
  }

  /**
   * Método para obtener los mensajes relacionados al PQR y construir una cadena para mostrar en la interfaz.
   */
  public obtenerMensajes() {
    let codigoPaciente = this.tokenService.getCodigo(); // Obtención del código del paciente a través del servicio TokenService

    // Verificación de que exista un PQR consultado actualmente
    if (this.pqrs) {
      let codigoPQRS = this.pqrs.codigo; // Obtención del código del PQR actualmente consultado

      // Llamada al servicio para listar los mensajes relacionados al PQR y el paciente
      this.pacienteService.listarMensajes(codigoPQRS, codigoPaciente).subscribe({
        next: data => {
          this.respuestaDTO = data.respuesta; // Asignación de las respuestas obtenidas al array respuestaDTO

          // Construcción de la cadena de mensajes para mostrar en la interfaz
          this.mensajesPQRS = this.respuestaDTO.map(item => ` ${item.fecha}: \n ${item.mensaje} - (${item.nombreUsuario}) \n \n`).join('\n');
        },
        error: error => {
          console.log(error); // Manejo de errores: imprimir el error en la consola (opcional)
        }
      });
    }
  }

  /**
   * Método para enviar una respuesta a un PQR.
   */
  public enviarMensaje() {
    this.registroRespuestaDTO.codigoPQRS = this.codigoPqrs; // Asignación del código del PQR a responder
    this.registroRespuestaDTO.codigoCuenta = this.tokenService.getCodigo(); // Asignación del código del paciente que envía la respuesta

    // Almacena la posición actual de desplazamiento
    const scrollPosition = window.scrollY;

    // Llamada al servicio para enviar la respuesta al PQR
    this.pacienteService.responderPQRS(this.registroRespuestaDTO).subscribe({
      next: data => {
        console.log(data); // Impresión en consola de la respuesta obtenida

        // Almacena la URL actual antes de la recarga
        const currentUrl = this.location.path();

        // Recarga la página sin cambiar la posición de desplazamiento
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
          // Vuelve a la URL original después de la recarga
          this.router.navigate([currentUrl]);

          // Restaura la posición de desplazamiento después de la recarga
          setTimeout(() => {
            window.scrollTo(0, scrollPosition);
          });
        });
      },
      error: error => {
        this.alerta = { tipo: "danger", mensaje: error.error.respuesta }; // Asignación de un mensaje de alerta en caso de error
        console.log(error); // Manejo de errores: imprimir el error en la consola (opcional)
      }
    });
  }
}
