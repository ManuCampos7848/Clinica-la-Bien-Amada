package co.uniquindio.clinicaLaBienAmada.controladores;


import co.uniquindio.clinicaLaBienAmada.dto.HorarioDTO;
import co.uniquindio.clinicaLaBienAmada.dto.ItemPQRSDTO;
import co.uniquindio.clinicaLaBienAmada.dto.TokenDTO.MensajeDTO;
import co.uniquindio.clinicaLaBienAmada.dto.paciente.ItemMedicoPacienteDTO;
import co.uniquindio.clinicaLaBienAmada.model.Ciudad;
import co.uniquindio.clinicaLaBienAmada.model.Eps;
import co.uniquindio.clinicaLaBienAmada.model.Especialidad;
import co.uniquindio.clinicaLaBienAmada.model.TipoDeSangre;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.ClinicaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinica")
@RequiredArgsConstructor
public class ClinicaController {

    private final ClinicaServicio clinicaServicio;

    /*
        Método que permite listar las ciudades, llama a un servicio para obtener la
        lista de ciudades, y envía una respuesta con la lista obtenida
     */
    @GetMapping("/lista-ciudades")
    public ResponseEntity<MensajeDTO<List<Ciudad>>> listarCiudades(){
        return ResponseEntity.ok().body( new MensajeDTO<>(false,
                clinicaServicio.listarCiudades()));
    }
    //________________________________________________________________________________________________________________

    /*
        Método que permite listar los tipos de sangre, llama a un servicio para obtener la lista de tipos de sangre,
     */
    @GetMapping("/lista-tipo-sangre")
    public ResponseEntity<MensajeDTO<List<TipoDeSangre>>> listarTiposSangre(){
        return ResponseEntity.ok().body( new MensajeDTO<>(false,
                clinicaServicio.listarTiposSangre()));
    }
    //________________________________________________________________________________________________________________
    /**
     * Método que permite listar las EPS, llama a un servicio para obtener la lista de EPS, y envía una respuesta con la lista obtenida.
     */
    @GetMapping("/lista-eps")
    public ResponseEntity<MensajeDTO<List<Eps>>> listarEps() {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, clinicaServicio.listarEps()));
    }

//________________________________________________________________________________________________________________

    /**
     * Método que permite listar las especialidades, llama a un servicio para obtener la lista de especialidades, y envía una respuesta con la lista obtenida.
     */
    @GetMapping("/lista-especialidad")
    public ResponseEntity<MensajeDTO<List<Especialidad>>> listarEspecialidades() {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, clinicaServicio.listarEspecialidades()));
    }

//________________________________________________________________________________________________________________

    /**
     * Método que permite listar los médicos por especialidad, llama a un servicio para obtener la lista de médicos según la especialidad, y envía una respuesta con la lista obtenida.
     */
    @GetMapping("/lista-medico-especialidad/{especialidad}")
    public ResponseEntity<MensajeDTO<List<ItemMedicoPacienteDTO>>> listarMedicoPorEspecialidad(@PathVariable Especialidad especialidad) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, clinicaServicio.listarMedicoPorEspecialidad(especialidad)));
    }

//________________________________________________________________________________________________________________

    /**
     * Método que permite listar los horarios de un médico, llama a un servicio para obtener la lista de horarios del médico, y envía una respuesta con la lista obtenida.
     */
    @GetMapping("/listar-horario-medico/{codigoMedico}")
    public ResponseEntity<MensajeDTO<List<HorarioDTO>>> listarHorariosMedico(@PathVariable int codigoMedico) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, clinicaServicio.listarHorariosMedico(codigoMedico)));
    }

//________________________________________________________________________________________________________________

    /**
     * Método que permite enviar un link de recuperación de contraseña, llama a un servicio para enviar el link de recuperación al correo proporcionado, y envía una respuesta indicando que el link fue enviado con éxito.
     */
    @PostMapping("/link-recuperacion-password")
    public ResponseEntity<MensajeDTO<String>> enviarLinkRecuperacion(@Valid @RequestBody String email) throws Exception {
        clinicaServicio.enviarLinkRecuperacion(email);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Link de recuperación enviado con exito"));
    }

//________________________________________________________________________________________________________________

}
