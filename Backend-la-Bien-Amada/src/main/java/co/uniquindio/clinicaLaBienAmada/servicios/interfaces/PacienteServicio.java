package co.uniquindio.clinicaLaBienAmada.servicios.interfaces;

import co.uniquindio.clinicaLaBienAmada.dto.*;
import co.uniquindio.clinicaLaBienAmada.dto.medico.DetalleAtencionMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.paciente.*;
import jakarta.el.ELException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface    PacienteServicio {

    int registrarse(RegistroPacienteDTO registroPacienteDTO) throws Exception;
    //________________________________________________________________________________________________________________

    int editarPerfil(DetallePacienteDTO detallePacienteDTO) throws Exception;
    //________________________________________________________________________________________________________________

    boolean eliminarCuenta(int codigoPaciente) throws Exception;
    //________________________________________________________________________________________________________________

    DetallePacienteDTO verDetallePaciente(int codigo) throws Exception;
    //________________________________________________________________________________________________________________
    void cambiarPassword(NuevaPasswordDTO nuevaPasswordDTO) throws Exception;
    //________________________________________________________________________________________________________________

    int agendarCita(RegistroCitaDTO registroCitaDTO) throws Exception;
    //________________________________________________________________________________________________________________

    int crearPQRS(RegistroPQRSDTO registroPQRSDTO) throws Exception;
    //________________________________________________________________________________________________________________

    List<ItemPQRSDTO> listarPQRSPciente(int codigoPciente) throws Exception;
    //________________________________________________________________________________________________________________

    DetallePQRSDTO verDetallePQRS (int codigo) throws Exception;
    //________________________________________________________________________________________________________________

    int responderPQRS(RegistroRespuestaDTO registroRespuestaDTO) throws Exception;
    //________________________________________________________________________________________________________________

    List<ItemCitaDTO> listarCitasPaciente(int codigoPaciente) throws Exception;
    //________________________________________________________________________________________________________________

    DetalleCitaDTO verDetalleCita(int codigoCita) throws Exception;
    //________________________________________________________________________________________________________________

    List<FiltroBusquedaDTO> filtrarCitasPorFecha(int codigoPaciente, LocalDate fecha) throws Exception;
    //________________________________________________________________________________________________________________

    List<FiltroBusquedaDTO> filtrarCitasPorMedico(int codigoPaciente, int codigoMedico) throws Exception;
    //________________________________________________________________________________________________________________

    List<RespuestaDTO> listarMensajes(int codigoPQRS, int codigoPaciente) throws Exception;
    //________________________________________________________________________________________________________________

    List<DetalleAtencionMedicoDTO> listarHistorialAtenciones(int codigoCita) throws Exception;
    //________________________________________________________________________________________________________________

    List<ItemCitaDTO> listarCitasCompletadasPaciente(int codigoMedico) throws Exception;
    //________________________________________________________________________________________________________________

    DetalleAtencionMedicoDTO verDetalleAtencion(int codigoCita) throws Exception;
    //________________________________________________________________________________________________________________

    List<DetalleAtencionMedicoDTO> filtrarAtencionesPorFecha(int codigoPaciente, LocalDate fecha) throws Exception;
    //________________________________________________________________________________________________________________

    List<DetalleAtencionMedicoDTO> filtrarAtencionesPorMedico(int codigoPaciente, int codigoMedico) throws Exception;
    //________________________________________________________________________________________________________________

}
