package co.uniquindio.clinicaLaBienAmada.servicios.interfaces;


import co.uniquindio.clinicaLaBienAmada.dto.EmailDTO;


public interface EmailServicio {

    void enviarCorreo(EmailDTO emailDTO) throws Exception;
    //________________________________________________________________________________________________________________



}
