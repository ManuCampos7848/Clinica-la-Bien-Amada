package co.uniquindio.clinicaLaBienAmada.dto.paciente;

import co.uniquindio.clinicaLaBienAmada.model.Ciudad;
import co.uniquindio.clinicaLaBienAmada.model.Eps;
import co.uniquindio.clinicaLaBienAmada.model.TipoDeSangre;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DetallePacienteDTO(
        int codigo,
        String cedula,
        String nombre,
        String telefono,
        String urlFoto,
        Ciudad ciudad,
        LocalDate fechaNacimiento,
        String alergias,
        Eps eps,
        TipoDeSangre tipoSangre,
        String correo
) {



}
