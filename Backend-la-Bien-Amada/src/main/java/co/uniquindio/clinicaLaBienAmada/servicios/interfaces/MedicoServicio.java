package co.uniquindio.clinicaLaBienAmada.servicios.interfaces;

import co.uniquindio.clinicaLaBienAmada.dto.DetalleCitaDTO;
import co.uniquindio.clinicaLaBienAmada.dto.medico.DetalleAtencionMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.ItemCitaDTO;
import co.uniquindio.clinicaLaBienAmada.dto.medico.DiaLibreDTO;
import co.uniquindio.clinicaLaBienAmada.dto.medico.RegistroAtencionDTO;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public interface MedicoServicio {

    List<ItemCitaDTO> listarCitasPendientes(int codigoMedico) throws Exception;
    //________________________________________________________________________________________________________________

    List<ItemCitaDTO> listarCitasCanceladas(int codigoMedico) throws Exception;
    //________________________________________________________________________________________________________________

    int atenderCita(RegistroAtencionDTO registroAtencionDTO) throws Exception;
    //________________________________________________________________________________________________________________

    List<DetalleAtencionMedicoDTO> listarHistorialAtenciones(int codigoPaciente) throws Exception;
    //________________________________________________________________________________________________________________

    int agendarDiaLibre(DiaLibreDTO diaLibreDTO) throws  Exception;
    //________________________________________________________________________________________________________________

    List<ItemCitaDTO> listarCitasRealizadasMedico(int codigoMedico) throws Exception;
    //________________________________________________________________________________________________________________

    DetalleAtencionMedicoDTO verDetalleAtencion(int codigoCita) throws Exception;
    //________________________________________________________________________________________________________________

    DetalleCitaDTO verDetalleCita(int codigoCita) throws Exception;
    //________________________________________________________________________________________________________________

    List<DiaLibreDTO> listarDiaslibres(int codigoMedico) throws Exception;
    //________________________________________________________________________________________________________________



}
