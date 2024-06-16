package co.uniquindio.clinicaLaBienAmada.dto;

import co.uniquindio.clinicaLaBienAmada.model.Especialidad;
import co.uniquindio.clinicaLaBienAmada.model.EstadoCita;
import co.uniquindio.clinicaLaBienAmada.model.Sede;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DetalleCitaDTO(int codigoCita,
                             String cedulaPaciente,
                             String nombrePaciente,
                             String nombreMedico,
                             int codigoMedico,
                             Especialidad especialidad,
                             EstadoCita estadoCita,
                             String motivo,
                             Sede sede,
                             LocalDate fecha) {
}
