package co.uniquindio.clinicaLaBienAmada.dto.paciente;

import co.uniquindio.clinicaLaBienAmada.model.EstadoPQRS;
import co.uniquindio.clinicaLaBienAmada.model.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegistroPQRSDTO(
        int codigoCita,
        String motivo,
        int codigoPaciente,
        EstadoPQRS estadoPQRS,
        LocalDate fechaCreacion,
        String tipo
) {
}
