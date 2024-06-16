package co.uniquindio.clinicaLaBienAmada.dto.medico;

import co.uniquindio.clinicaLaBienAmada.model.Especialidad;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DetalleAtencionMedicoDTO(
        int codigoCita,
        String nombrePaciente,
        String nombreMedico,
        int codigoMedico,
        Especialidad especialidad,
        LocalDate fechaAtencion,
        String tratamiento,
        String notasMedicas,
        String diagnostico
) {
}
