package co.uniquindio.clinicaLaBienAmada.dto;

import co.uniquindio.clinicaLaBienAmada.model.EstadoPQRS;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ItemPQRSDTO(
        int codigo,
        EstadoPQRS estado,
        String motivo,
        LocalDate fecha,
        String nombrePaciente) {
}
