package co.uniquindio.clinicaLaBienAmada.dto.paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FiltroBusquedaDTO(
        int codigoCita,
        int codigoMedico,
        String nombreMedico,
        String motivo,
        LocalDate fecha
) {


}
