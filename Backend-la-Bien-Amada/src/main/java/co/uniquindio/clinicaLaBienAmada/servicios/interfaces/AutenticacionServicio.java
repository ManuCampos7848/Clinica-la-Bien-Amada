package co.uniquindio.clinicaLaBienAmada.servicios.interfaces;

import co.uniquindio.clinicaLaBienAmada.dto.LoginDTO;
import co.uniquindio.clinicaLaBienAmada.dto.TokenDTO.TokenDTO;

public interface AutenticacionServicio {
    TokenDTO login(LoginDTO loginDTO) throws Exception;
    //________________________________________________________________________________________________________________

}
