package co.uniquindio.clinicaLaBienAmada.dto.paciente;

import co.uniquindio.clinicaLaBienAmada.dto.HorarioDTO;
import co.uniquindio.clinicaLaBienAmada.model.Horario;

import java.util.List;

public record ItemMedicoPacienteDTO(
        int codigoMedico,
        String nombreMedico,
        String correoMedico,
        List<HorarioDTO> horarios) {
}
