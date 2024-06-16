package co.uniquindio.clinicaLaBienAmada.dto.admin;

import co.uniquindio.clinicaLaBienAmada.model.Especialidad;

public record ItemMedicoDTO(
        int codigo,
        String cedula,
        String nombre,
        String urlFoto,
        Especialidad especialidad
    ){
}
