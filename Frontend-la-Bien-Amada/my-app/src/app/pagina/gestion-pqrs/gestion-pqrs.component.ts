import { Component } from '@angular/core';
import { ItemPQRSDTO } from 'src/app/modelo/ItemPQRSDTO'; // Importa el modelo ItemPQRSDTO
import { PacienteService } from 'src/app/servicios/paciente.service'; // Importa el servicio PacienteService
import { TokenService } from 'src/app/servicios/token.service'; // Importa el servicio TokenService

@Component({
  selector: 'app-gestion-pqrs',
  templateUrl: './gestion-pqrs.component.html',
  styleUrls: ['./gestion-pqrs.component.css']
})
export class GestionPqrsComponent {

  pqrs: ItemPQRSDTO[]; // Array para almacenar las PQRs del paciente

  constructor(private pacienteService: PacienteService, private tokenService: TokenService) {
    // Inicializa el array de PQRs
    this.pqrs = [];
    // Llama al método para obtener las PQRs al inicializar el componente
    this.obtenerPqrs();
  }

  /**
   * Método para obtener las PQRs del paciente.
   * Utiliza el servicio PacienteService para obtener las PQRs y las asigna a pqrs.
   */
  public obtenerPqrs() {
    let codigo = this.tokenService.getCodigo(); // Obtiene el código del paciente autenticado desde el servicio TokenService
    this.pacienteService.listarPQRSPaciente(codigo).subscribe({
      next: data => {
        this.pqrs = data.respuesta; // Asigna las PQRs obtenidas al array pqrs
      },
      error: error => {
        console.log(error); // Maneja errores imprimiendo en consola
      }
    });
  }

}
