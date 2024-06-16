package co.uniquindio.clinicaLaBienAmada.servicios.interfaces;

import co.uniquindio.clinicaLaBienAmada.dto.*;
import co.uniquindio.clinicaLaBienAmada.dto.paciente.ItemPacienteDTO;
import co.uniquindio.clinicaLaBienAmada.model.EstadoPQRS;
import co.uniquindio.clinicaLaBienAmada.dto.admin.DetalleMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.admin.ItemMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.admin.RegistroMedicoDTO;

import java.util.List;

public interface AdmnistradorServicio {

    int crearMedico(RegistroMedicoDTO medico) throws Exception;
    //________________________________________________________________________________________________________________


    int actualizarMedico(DetalleMedicoDTO medico) throws Exception;
    //________________________________________________________________________________________________________________


    boolean eliminarMedico(int codigo) throws Exception;
    //________________________________________________________________________________________________________________


    List<ItemMedicoDTO> listarMedicos() throws Exception;
    //________________________________________________________________________________________________________________


    DetalleMedicoDTO obtenerMedico(int codigo) throws Exception;
    //________________________________________________________________________________________________________________


    List<ItemPQRSDTO> listarPQRS() throws Exception;
    //________________________________________________________________________________________________________________


    DetallePQRSDTO verDetallePQRS(int codigo) throws Exception;
    //________________________________________________________________________________________________________________


    int responderPQRS(RegistroRespuestaDTO registroRespuestaDTO) throws  Exception;
    //________________________________________________________________________________________________________________


    List<ItemCitaDTO> listarCitas() throws Exception;
    //________________________________________________________________________________________________________________


    int cambiarEstadoPQRS(int codigoPQRS, EstadoPQRS estadoPQRS) throws Exception;
    //________________________________________________________________________________________________________________

    List<ItemPacienteDTO> listarTodos() throws Exception;
    //________________________________________________________________________________________________________________


}
