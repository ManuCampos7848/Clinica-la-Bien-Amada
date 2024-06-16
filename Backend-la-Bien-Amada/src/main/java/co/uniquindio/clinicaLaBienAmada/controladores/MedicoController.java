package co.uniquindio.clinicaLaBienAmada.controladores;

import co.uniquindio.clinicaLaBienAmada.dto.DetalleCitaDTO;
import co.uniquindio.clinicaLaBienAmada.dto.ItemCitaDTO;
import co.uniquindio.clinicaLaBienAmada.dto.TokenDTO.MensajeDTO;
import co.uniquindio.clinicaLaBienAmada.dto.medico.DetalleAtencionMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.medico.DiaLibreDTO;
import co.uniquindio.clinicaLaBienAmada.dto.medico.RegistroAtencionDTO;
import co.uniquindio.clinicaLaBienAmada.dto.paciente.RegistroPacienteDTO;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.MedicoServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoServicio medicoServicio;

    /**
     * Método que permite listar las citas pendientes de un médico, llama a un servicio para obtener la lista de citas pendientes, y envía una respuesta con la lista obtenida.
     */
    @GetMapping("/citas-pendientes/{codigoMedico}")
    public ResponseEntity<MensajeDTO<List<ItemCitaDTO>>> listarCitasPendientes(@PathVariable int codigoMedico) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, medicoServicio.listarCitasPendientes(codigoMedico)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite listar las citas realizadas de un médico, llama a un servicio para obtener la lista de citas realizadas, y envía una respuesta con la lista obtenida.
     */
    @GetMapping("/citas-realizadas/{codigoMedico}")
    public ResponseEntity<MensajeDTO<List<ItemCitaDTO>>> listarCitasRealizadas(@PathVariable int codigoMedico) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, medicoServicio.listarCitasRealizadasMedico(codigoMedico)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite listar las citas canceladas de un médico, llama a un servicio para obtener la lista de citas canceladas, y envía una respuesta con la lista obtenida.
     */
    @GetMapping("/citas-canceladas/{codigoMedico}")
    public ResponseEntity<MensajeDTO<List<ItemCitaDTO>>> listarCitasCanceladas(@PathVariable int codigoMedico) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, medicoServicio.listarCitasCanceladas(codigoMedico)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite atender una cita, recibe los datos de atención, llama a un servicio para registrar la atención, y envía una respuesta indicando que la atención fue realizada con éxito.
     */
    @PostMapping("/atender-cita")
    public ResponseEntity<MensajeDTO<String>> atenderCita(@Valid @RequestBody RegistroAtencionDTO registroAtencionDTO) throws Exception {
        medicoServicio.atenderCita(registroAtencionDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Atención realizada con exito"));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite ver el detalle de atención de una cita, llama a un servicio para obtener los detalles de la atención, y envía una respuesta con los detalles obtenidos.
     */
    @GetMapping("/detalle-atencion/{codigoCita}")
    public ResponseEntity<MensajeDTO<DetalleAtencionMedicoDTO>> verDetalleAtencion(@PathVariable int codigoCita) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, medicoServicio.verDetalleAtencion(codigoCita)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite listar el historial de atenciones de una cita, llama a un servicio para obtener el historial, y envía una respuesta con la lista obtenida.
     */
    @GetMapping("/atenciones/{codigoCita}")
    public ResponseEntity<MensajeDTO<List<DetalleAtencionMedicoDTO>>> listarHistorialAtenciones(@PathVariable int codigoCita) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, medicoServicio.listarHistorialAtenciones(codigoCita)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite agendar un día libre para un médico, recibe los datos del día libre, llama a un servicio para agendarlo, y envía una respuesta indicando que el día libre fue agendado con éxito.
     */
    @PostMapping("/agendar-dia-libre")
    public ResponseEntity<MensajeDTO<String>> agendarDiaLibre(@Valid @RequestBody DiaLibreDTO diaLibreDTO) throws Exception {
        medicoServicio.agendarDiaLibre(diaLibreDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Dia libre agendado con exito"));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite listar los días libres de un médico, llama a un servicio para obtener la lista de días libres, y envía una respuesta con la lista obtenida.
     */
    @GetMapping("/listar-dias-libres/{codigoMedico}")
    public ResponseEntity<MensajeDTO<List<DiaLibreDTO>>> listarDiasLibres(@PathVariable int codigoMedico) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, medicoServicio.listarDiaslibres(codigoMedico)));
    }

    //________________________________________________________________________________________________________________

    /**
     * Método que permite ver el detalle de una cita, llama a un servicio para obtener los detalles de la cita, y envía una respuesta con los detalles obtenidos.
     */
    @GetMapping("/detalle-cita/{codigoCita}")
    public ResponseEntity<MensajeDTO<DetalleCitaDTO>> verDetalleCita(@PathVariable int codigoCita) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, medicoServicio.verDetalleCita(codigoCita)));
    }

    //________________________________________________________________________________________________________________
}
