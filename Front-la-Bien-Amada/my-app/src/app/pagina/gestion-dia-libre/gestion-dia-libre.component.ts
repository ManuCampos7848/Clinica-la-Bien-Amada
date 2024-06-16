import { Component } from '@angular/core';
import { DiaLibreDTO } from 'src/app/modelo/DiaLibreDTO'; // Importa el modelo DiaLibreDTO
import { MedicoService } from 'src/app/servicios/medico.service'; // Importa el servicio MedicoService
import { TokenService } from 'src/app/servicios/token.service'; // Importa el servicio TokenService

@Component({
  selector: 'app-gestion-dia-libre',
  templateUrl: './gestion-dia-libre.component.html',
  styleUrls: ['./gestion-dia-libre.component.css']
})
export class GestionDiaLibreComponent {

  diasLibres: DiaLibreDTO[]; // Array para almacenar los días libres del médico

  constructor(private tokenService: TokenService, private medicoService: MedicoService) {
    // Inicializa el array de días libres
    this.diasLibres = [];
    // Llama al método para obtener los días libres al inicializar el componente
    this.obtencionDiasLibres();
  }

  /**
   * Método para obtener los días libres del médico.
   * Utiliza el servicio MedicoService para obtener los días libres y los asigna a diasLibres.
   */
  public obtencionDiasLibres() {
    let codigo = this.tokenService.getCodigo(); // Obtiene el código del médico autenticado desde el servicio TokenService
    this.medicoService.listarDiasLibres(codigo).subscribe({
      next: data => {
        this.diasLibres = data.respuesta; // Asigna los días libres obtenidos al array diasLibres
      },
      error: error => {
        console.log(error); // Maneja errores imprimiendo en consola
      }
    });
  }

}
