package co.uniquindio.clinicaLaBienAmada.dto.TokenDTO;

public record MensajeDTO<T>(
        boolean error,
        T respuesta) {
}
