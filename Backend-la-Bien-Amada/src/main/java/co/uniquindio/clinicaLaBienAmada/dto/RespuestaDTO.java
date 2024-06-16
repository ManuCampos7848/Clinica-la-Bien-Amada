package co.uniquindio.clinicaLaBienAmada.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record RespuestaDTO( int codigoMensaje,
        String mensaje,
        String nombreUsuario,
        LocalDate fecha) {
}

