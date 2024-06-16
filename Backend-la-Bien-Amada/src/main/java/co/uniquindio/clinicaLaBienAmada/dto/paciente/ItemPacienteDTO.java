package co.uniquindio.clinicaLaBienAmada.dto.paciente;

import co.uniquindio.clinicaLaBienAmada.model.Ciudad;

public record ItemPacienteDTO(
        int codigo,
        String cedula,
        String nombre,
        Ciudad ciudad
) {
}
