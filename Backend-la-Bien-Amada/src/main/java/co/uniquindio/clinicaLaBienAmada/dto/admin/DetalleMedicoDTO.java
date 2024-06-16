package co.uniquindio.clinicaLaBienAmada.dto.admin;

import co.uniquindio.clinicaLaBienAmada.model.Ciudad;
import co.uniquindio.clinicaLaBienAmada.model.Especialidad;
import co.uniquindio.clinicaLaBienAmada.dto.HorarioDTO;
import co.uniquindio.clinicaLaBienAmada.model.Horario;

import java.util.List;

public record DetalleMedicoDTO(
        int codigo,
        String nombre,
        String cedula,
        Ciudad ciudad,
        Especialidad especialidad,
        String telefono,
        String correo,
        String urlFoto,
        List<HorarioDTO> horarios

) {

}
