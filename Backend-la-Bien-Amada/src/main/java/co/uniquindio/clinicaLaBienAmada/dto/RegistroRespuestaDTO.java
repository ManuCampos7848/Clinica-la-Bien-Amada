package co.uniquindio.clinicaLaBienAmada.dto;

import co.uniquindio.clinicaLaBienAmada.model.Mensaje;

public record RegistroRespuestaDTO(
        int codigoCuenta,
        int codigoPQRS,
        String mensaje
       // Mensaje
) {
}
