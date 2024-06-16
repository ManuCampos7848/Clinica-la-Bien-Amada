package co.uniquindio.clinicaLaBienAmada.controladores;


import co.uniquindio.clinicaLaBienAmada.dto.DetallePQRSDTO;
import co.uniquindio.clinicaLaBienAmada.dto.ItemCitaDTO;
import co.uniquindio.clinicaLaBienAmada.dto.ItemPQRSDTO;
import co.uniquindio.clinicaLaBienAmada.dto.RegistroRespuestaDTO;
import co.uniquindio.clinicaLaBienAmada.dto.TokenDTO.MensajeDTO;
import co.uniquindio.clinicaLaBienAmada.dto.admin.DetalleMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.admin.ItemMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.admin.RegistroMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.paciente.ItemPacienteDTO;
import co.uniquindio.clinicaLaBienAmada.dto.paciente.RegistroPacienteDTO;
import co.uniquindio.clinicaLaBienAmada.model.EstadoPQRS;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.AdmnistradorServicio;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.PacienteServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdmnistradorServicio admnistradorServicio;

    /*
        Metodo que permite listar todos los pacientes
     */
    @GetMapping("/listar-todos")
    public ResponseEntity<MensajeDTO<List<ItemPacienteDTO>>> listarTodos() throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, admnistradorServicio.listarTodos()) );
    }
    //________________________________________________________________________________________________________________

    /*
        Metodo que permite registrar un medico
     */
    @PostMapping("/registrar-medico")
    public ResponseEntity<MensajeDTO<String>> registrarse(@Valid @RequestBody RegistroMedicoDTO medicoDTO) throws Exception {
        admnistradorServicio.crearMedico(medicoDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Medico registrado correctamente"));
    }
    //________________________________________________________________________________________________________________


    /*
        Metodo que permite actualizar los datos de un medico
     */
    @PutMapping("/actualizar-medico")
    public ResponseEntity<MensajeDTO<String>> actualizarMedico(@Valid @RequestBody DetalleMedicoDTO medicoDTO) throws Exception {
        admnistradorServicio.actualizarMedico(medicoDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Medico actualizado correctamente"));
    }
    //________________________________________________________________________________________________________________

    /*
        Metodo que permite eliminar un meidco en base a su codigo
     */
    @DeleteMapping("/eliminar-medico/{codigoMedico}")
    public ResponseEntity<MensajeDTO<String>> eliminarMedico(@PathVariable int codigoMedico) throws Exception {
        admnistradorServicio.eliminarMedico(codigoMedico);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Medico eliminado correctamente"));
    }
    //________________________________________________________________________________________________________________

    /*
        Metodo que permite listar todos los medicos
     */
    @GetMapping("/listar-medicos")
    public ResponseEntity<MensajeDTO<List<ItemMedicoDTO>>> listarMedicos() throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, admnistradorServicio.listarMedicos()) );
    }
    //________________________________________________________________________________________________________________

    /*
        Metodo que permite ver los datos de un medico en base a su codigo
     */
    @GetMapping("/detalle-medico/{codigoMedico}")
    public ResponseEntity<MensajeDTO<DetalleMedicoDTO>> detalleMedico(@PathVariable int codigoMedico) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false,
                admnistradorServicio.obtenerMedico(codigoMedico)));
    }
    //________________________________________________________________________________________________________________

    /*
        Metodo que permite listar todas las PQRS de las citas realizadas
     */
    @GetMapping("/listar-pqrs")
    public ResponseEntity<MensajeDTO<List<ItemPQRSDTO>>> listarPQRS() throws Exception {
        return  ResponseEntity.ok().body(new MensajeDTO<>(false,
                admnistradorServicio.listarPQRS()));
    }
    //________________________________________________________________________________________________________________

    /*
        Metodo que permite ver los detalles de una PQRS de una cita realizada en base al codigo
     */
    @GetMapping("/detalle-pqrs/{codigoPQRS}")
    public ResponseEntity<MensajeDTO<DetallePQRSDTO>> verDetallePQRS(@PathVariable int codigoPQRS) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false,
                admnistradorServicio.verDetallePQRS(codigoPQRS)));
    }
    //________________________________________________________________________________________________________________

    /*
        Metodo que permite que el administrador responda una PQRS a algun paciente sobre una
        cita realizada con anterioridad
     */
    @PostMapping("/responder-PQRS")
    public ResponseEntity<MensajeDTO<String>> responderPQRS(@Valid @RequestBody RegistroRespuestaDTO respuestaDTO) throws Exception {
        admnistradorServicio.responderPQRS(respuestaDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Respuesta registrada con exito"));
    }
    //________________________________________________________________________________________________________________

    /*
        Metodo que permite actualizar el estado de una PQRS (Nueva, en proceso, resuelta o archivada)
     */
    @PutMapping("/cambiar-estado-pqr/{codigoPQRS}/{estadoPQRS}")
    public ResponseEntity<MensajeDTO<String>> cambiarEstadoPQRS(@PathVariable int codigoPQRS, @PathVariable  EstadoPQRS estadoPQRS) throws Exception {
        admnistradorServicio.cambiarEstadoPQRS(codigoPQRS, estadoPQRS);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Estado actualizado correctamente"));

    }
    //________________________________________________________________________________________________________________

    /*
        Metodo que permite listar todas las citas sin importar el estado de esta
     */
    @GetMapping("/listar-citas")
    public ResponseEntity<MensajeDTO<List<ItemCitaDTO>>> listarCitasPaciente() throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false,
                admnistradorServicio.listarCitas()));
    }
    //________________________________________________________________________________________________________________
}
