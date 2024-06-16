package co.uniquindio.clinicaLaBienAmada.dto.admin;

import co.uniquindio.clinicaLaBienAmada.model.Especialidad;
import co.uniquindio.clinicaLaBienAmada.model.EstadoCita;

import java.time.LocalDateTime;

public record ItemCitaAdminDTO(
        int codigoCita,
        String cedulaPaciente,
        String nombrePaciente,
        String nombreMedico,
        Especialidad especialidad,
        EstadoCita estadoCita,
        LocalDateTime fecha
) {
}
