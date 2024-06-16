package co.uniquindio.clinicaLaBienAmada.servicios.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImagenesServicio {
    Map subirImagen(MultipartFile imagen) throws Exception;
    //________________________________________________________________________________________________________________


    Map eliminarImagen(String idImagen)throws Exception;
    //________________________________________________________________________________________________________________

}
