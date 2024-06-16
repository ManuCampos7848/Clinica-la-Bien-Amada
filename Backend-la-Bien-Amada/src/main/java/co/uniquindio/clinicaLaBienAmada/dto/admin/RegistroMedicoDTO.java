package co.uniquindio.clinicaLaBienAmada.dto.admin;

import co.uniquindio.clinicaLaBienAmada.model.Ciudad;
import co.uniquindio.clinicaLaBienAmada.model.Especialidad;
import co.uniquindio.clinicaLaBienAmada.dto.HorarioDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record RegistroMedicoDTO(
        @NotNull
        @Length(max = 200)
        String nombre,

        @NotNull
        @Length(max = 200)
        String cedula,
        @NotNull
        Ciudad ciudad,
        Especialidad especialidad,
        @NotNull
        @Length(max = 20)
        String telefono,
        @NotNull
        @Email
        @Length(max = 80)
        String correo,

        @NotNull
        @Length(max = 200)
        String password,
        @NotNull
        String URLFoto,
        @NotNull
        boolean estado,
        @NotNull
        List<HorarioDTO> horarios
) {


}
