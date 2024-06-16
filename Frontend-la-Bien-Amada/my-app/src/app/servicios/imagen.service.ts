import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MensajeDTO } from '../modelo/mensaje-dto';
import { ImagenDTO } from '../modelo/ImagenDTO';

@Injectable({
  providedIn: 'root'
})
export class ImagenService {

  /**
   * URL 
   */
  private imgURL = "http://localhost:8080/api/imagenes";
  
  constructor(private http: HttpClient) { }

  /**
   * Metodos que realizan las peticiones al servidor (POST, REQUEST)
   */
  public subir(imagen: FormData): Observable<MensajeDTO> {
    return this.http.post<MensajeDTO>(`${this.imgURL}/subir`, imagen);
  }

  /**
   * Metodo que realiza la peticion para eliminar la imagen 
   * @param imagenDTO 
   * @returns 
   */
  public eliminar(imagenDTO: ImagenDTO): Observable<MensajeDTO> {
    return this.http.request<MensajeDTO>('delete', `${this.imgURL}/eliminar`, {
      body:

        imagenDTO
    });
  }

}
