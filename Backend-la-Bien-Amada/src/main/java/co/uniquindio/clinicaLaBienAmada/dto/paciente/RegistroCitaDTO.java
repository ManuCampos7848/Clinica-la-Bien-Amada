package co.uniquindio.clinicaLaBienAmada.dto.paciente;

import co.uniquindio.clinicaLaBienAmada.model.EstadoCita;
import co.uniquindio.clinicaLaBienAmada.model.Sede;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegistroCitaDTO(

        LocalDate fechaCita,
        String horaCita,
        String motivo,
        EstadoCita estadoCita,
        Sede sede,
        int codigoPaciente,
        int codigoMedico

) {

}
