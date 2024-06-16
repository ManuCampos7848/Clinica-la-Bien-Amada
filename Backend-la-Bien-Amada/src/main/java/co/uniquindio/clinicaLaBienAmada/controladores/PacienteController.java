package co.uniquindio.clinicaLaBienAmada.controladores;

import co.uniquindio.clinicaLaBienAmada.dto.*;
import co.uniquindio.clinicaLaBienAmada.dto.TokenDTO.MensajeDTO;
import co.uniquindio.clinicaLaBienAmada.dto.medico.DetalleAtencionMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.paciente.*;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.PacienteServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteServicio pacienteServicio;

    /**
     * Método que permite agendar una cita para un paciente, recibe los datos de la cita, llama a un servicio para agendarla, y envía una respuesta indicando que la cita fue agendada correctamente.
     */
    @PostMapping("/agendar-cita")
    public ResponseEntity<MensajeDTO<String>> agendarCita(@Valid @RequestBody RegistroCitaDTO registroCitaDTO) throws Exception {
        pacienteServicio.agendarCita(registroCitaDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Cita agendada correctamente"));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite crear una PQRS (Petición, Queja, Reclamo, Sugerencia) para un paciente, recibe los datos de la PQRS, llama a un servicio para crearla, y envía una respuesta indicando que la PQRS fue creada con éxito.
     */
    @PostMapping("/crear-pqrs")
    public ResponseEntity<MensajeDTO<String>> crearPQRS(@Valid @RequestBody RegistroPQRSDTO registroPQRSDTO) throws Exception {
        pacienteServicio.crearPQRS(registroPQRSDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "PQRS creada con exito"));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite editar el perfil de un paciente, recibe los datos actualizados del paciente, llama a un servicio para actualizar el perfil, y envía una respuesta indicando que el paciente fue actualizado correctamente.
     */
    @PutMapping("/editar-perfil")
    public ResponseEntity<MensajeDTO<String>> editarPerfil(@Valid @RequestBody DetallePacienteDTO pacienteDTO) throws Exception {
        pacienteServicio.editarPerfil(pacienteDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Paciente actualizado correctamente"));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite eliminar la cuenta de un paciente, recibe el código del paciente, llama a un servicio para eliminar la cuenta, y envía una respuesta indicando que el paciente fue eliminado correctamente.
     */
    @DeleteMapping("/eliminar/{codigo}")
    public ResponseEntity<MensajeDTO<String>> eliminarCuenta(@PathVariable int codigo) throws Exception {
        pacienteServicio.eliminarCuenta(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Paciente eliminado correctamente"));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite ver el detalle de un paciente, recibe el código del paciente, llama a un servicio para obtener los detalles del paciente, y envía una respuesta con los detalles obtenidos.
     */
    @GetMapping("/detalle-paciente/{codigo}")
    public ResponseEntity<MensajeDTO<DetallePacienteDTO>> verDetallePaciente(@PathVariable int codigo) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.verDetallePaciente(codigo)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite listar las PQRS de un paciente, recibe el código del paciente, llama a un servicio para obtener la lista de PQRS, y envía una respuesta con la lista obtenida.
     */
    @GetMapping("/listar-pqrs-paciente/{codigoPaciente}")
    public ResponseEntity<MensajeDTO<List<ItemPQRSDTO>>> listarPQRSPaciente(@PathVariable int codigoPaciente) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.listarPQRSPciente(codigoPaciente)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite listar las citas de un paciente, recibe el código del paciente, llama a un servicio para obtener la lista de citas, y envía una respuesta con la lista obtenida.
     */
    @GetMapping("/listar-citas-paciente/{codigoPaciente}")
    public ResponseEntity<MensajeDTO<List<ItemCitaDTO>>> listarCitasPaciente(@PathVariable int codigoPaciente) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.listarCitasPaciente(codigoPaciente)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite ver el detalle de una cita de un paciente, recibe el código de la cita, llama a un servicio para obtener los detalles de la cita, y envía una respuesta con los detalles obtenidos.
     */
    @GetMapping("/detalle-cita/{codigoCita}")
    public ResponseEntity<MensajeDTO<DetalleCitaDTO>> verDetalleCita(@PathVariable int codigoCita) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.verDetalleCita(codigoCita)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite filtrar citas por fecha para un paciente, recibe el código del paciente y la fecha, llama a un servicio para filtrar las citas por fecha, y envía una respuesta con la lista de citas obtenida.
     */
    @GetMapping("/filtrar-cita-fecha/{codigoPaciente}/{fecha}")
    public ResponseEntity<MensajeDTO<List<FiltroBusquedaDTO>>> filtrarCitasPorFecha(@PathVariable int codigoPaciente, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.filtrarCitasPorFecha(codigoPaciente, fecha)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite filtrar citas por médico para un paciente, recibe el código del paciente y el código del médico, llama a un servicio para filtrar las citas por médico, y envía una respuesta con la lista de citas obtenida.
     */
    @GetMapping("/filtrar-cita-medico/{codigoPaciente}/{codigoMedico}")
    public ResponseEntity<MensajeDTO<List<FiltroBusquedaDTO>>> filtrarCitasPorMedico(@PathVariable int codigoPaciente, @PathVariable int codigoMedico) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.filtrarCitasPorMedico(codigoPaciente, codigoMedico)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite ver el detalle de una PQRS de un paciente, recibe el código de la PQRS, llama a un servicio para obtener los detalles de la PQRS, y envía una respuesta con los detalles obtenidos.
     */
    @GetMapping("/detalle-pqrs/{codigo}")
    public ResponseEntity<MensajeDTO<DetallePQRSDTO>> verDetallePQRS(@PathVariable int codigo) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.verDetallePQRS(codigo)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite responder a una PQRS, recibe los datos de la respuesta, llama a un servicio para enviar la respuesta, y envía una respuesta indicando que el mensaje fue enviado con éxito.
     */
    @PostMapping("/responder-pqrs")
    public ResponseEntity<MensajeDTO<String>> responderPQRS(@Valid @RequestBody RegistroRespuestaDTO registroRespuestaDTO) throws Exception {
        pacienteServicio.responderPQRS(registroRespuestaDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Mensaje enviado con exito"));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite listar los mensajes de una PQRS para un paciente, recibe el código de la PQRS y el código del paciente, llama a un servicio para obtener la lista de mensajes, y envía una respuesta con la lista obtenida.
     */
    @GetMapping("/listar-mensajes/{codigoPQRS}/{codigoPaciente}")
    public ResponseEntity<MensajeDTO<List<RespuestaDTO>>> listarMensajes(@PathVariable int codigoPQRS, @PathVariable int codigoPaciente) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.listarMensajes(codigoPQRS, codigoPaciente)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite listar el historial de atenciones de una cita para un paciente, recibe el código de la cita, llama a un servicio para obtener el historial de atenciones, y envía una respuesta con la lista obtenida.
     */
    @GetMapping("/atenciones/{codigoCita}")
    public ResponseEntity<MensajeDTO<List<DetalleAtencionMedicoDTO>>> listarHistorialAtenciones(@PathVariable int codigoCita) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.listarHistorialAtenciones(codigoCita)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite listar las citas completadas de un paciente, recibe el código del paciente, llama a un servicio para obtener la lista de citas completadas, y envía una respuesta con la lista obtenida.
     */
    @GetMapping("/citas-completadas/{codigoPaciente}")
    public ResponseEntity<MensajeDTO<List<ItemCitaDTO>>> listarCitasRealizadas(@PathVariable int codigoPaciente) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.listarCitasCompletadasPaciente(codigoPaciente)));
    }
    //________________________________________________________________________________________________________________



    // Obtiene y devuelve los detalles de la atención médica para una cita específica.
    @GetMapping("/detalle-atencion/{codigoCita}")
    public ResponseEntity<MensajeDTO<DetalleAtencionMedicoDTO>> verDetalleAtencion(@PathVariable int codigoCita) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.verDetalleAtencion(codigoCita)));
    }
    //________________________________________________________________________________________________________________


    // Filtra y devuelve las atenciones médicas de un paciente para una fecha específica.
    @GetMapping("/filtrar-atencion-fecha/{codigoPaciente}/{fecha}")
    public ResponseEntity<MensajeDTO<List<DetalleAtencionMedicoDTO>>> filtrarAtencionesPorFecha(@PathVariable int codigoPaciente, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.filtrarAtencionesPorFecha(codigoPaciente, fecha)));
    }
    //________________________________________________________________________________________________________________


    // Filtra y devuelve las atenciones médicas de un paciente por un médico específico.
    @GetMapping("/filtrar-atencion-medico/{codigoPaciente}/{codigoMedico}")
    public ResponseEntity<MensajeDTO<List<DetalleAtencionMedicoDTO>>> filtrarAtencionesPorMedico(@PathVariable int codigoPaciente, @PathVariable int codigoMedico) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.filtrarAtencionesPorMedico(codigoPaciente, codigoMedico)));
    }
    //________________________________________________________________________________________________________________


}
