package co.uniquindio.clinicaLaBienAmada.servicios.impl;

import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.ImagenesServicio;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImagenesServicioImpl implements ImagenesServicio {

    private final Cloudinary cloudinary;

    /**
     * Constructor de la clase ImagenesServicioImpl que inicializa Cloudinary con la configuración necesaria.
     */
    public ImagenesServicioImpl(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dex5cjoez");
        config.put("api_key", "966989232443354");
        config.put("api_secret", "nt7sFxaIr6QayitZsmiugZ9pAe8");
        cloudinary = new Cloudinary(config);
    }
    //________________________________________________________________________________________________________________

    /**
     * Sube una imagen a Cloudinary.
     *
     * @param imagen El archivo de imagen a subir.
     * @return Un mapa que contiene la información de la imagen subida.
     * @throws Exception Si ocurre algún error durante la subida de la imagen.
     */
    @Override
    public Map subirImagen(MultipartFile imagen) throws Exception {
        File file = convertir(imagen);

        return cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", "uniquindio/proyecto/fotos"));
    }
    //________________________________________________________________________________________________________________

    /**
     * Elimina una imagen de Cloudinary.
     *
     * @param idImagen El ID de la imagen en Cloudinary que se desea eliminar.
     * @return Un mapa que contiene la respuesta de Cloudinary tras intentar eliminar la imagen.
     * @throws Exception Si ocurre algún error durante la eliminación de la imagen.
     */
    @Override
    public Map eliminarImagen(String idImagen) throws Exception {
        return cloudinary.uploader().destroy(idImagen, ObjectUtils.emptyMap());
    }
    //________________________________________________________________________________________________________________

    /**
     * Convierte un objeto MultipartFile a un archivo File.
     *
     * @param imagen El objeto MultipartFile que se desea convertir.
     * @return El archivo File resultante de la conversión.
     * @throws IOException Si ocurre un error de entrada/salida durante la conversión.
     */
    private File convertir(MultipartFile imagen) throws IOException {
        File file = File.createTempFile(imagen.getOriginalFilename(), null);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imagen.getBytes());
        fos.close();
        return file;
    }
    //________________________________________________________________________________________________________________

}
