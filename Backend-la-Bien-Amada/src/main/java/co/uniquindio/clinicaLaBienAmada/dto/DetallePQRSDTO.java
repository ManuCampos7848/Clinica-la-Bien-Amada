package co.uniquindio.clinicaLaBienAmada.dto;

import co.uniquindio.clinicaLaBienAmada.model.Especialidad;
import co.uniquindio.clinicaLaBienAmada.model.EstadoPQRS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record DetallePQRSDTO(
        int codigo,
        EstadoPQRS estado,
        String motivoPQRS,
        String nombrePaciente,
        String nombreMedico,
        Especialidad especialidad,
        LocalDate fecha,
        List<RespuestaDTO> mensajes){

}
