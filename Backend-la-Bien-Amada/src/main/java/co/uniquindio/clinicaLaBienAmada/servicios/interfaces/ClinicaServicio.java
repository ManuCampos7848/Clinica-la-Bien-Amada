package co.uniquindio.clinicaLaBienAmada.servicios.interfaces;

import co.uniquindio.clinicaLaBienAmada.dto.HorarioDTO;
import co.uniquindio.clinicaLaBienAmada.dto.paciente.ItemMedicoPacienteDTO;
import co.uniquindio.clinicaLaBienAmada.model.Ciudad;
import co.uniquindio.clinicaLaBienAmada.model.Eps;
import co.uniquindio.clinicaLaBienAmada.model.Especialidad;
import co.uniquindio.clinicaLaBienAmada.model.TipoDeSangre;

import java.util.List;

public interface ClinicaServicio {

    List<Ciudad> listarCiudades();
    //________________________________________________________________________________________________________________

    List<Especialidad> listarEspecialidades();
    //________________________________________________________________________________________________________________

    List<TipoDeSangre> listarTiposSangre();
    //________________________________________________________________________________________________________________

    List<Eps> listarEps();
    //________________________________________________________________________________________________________________


    List<ItemMedicoPacienteDTO> listarMedicoPorEspecialidad(Especialidad especialidad) throws Exception;
    //________________________________________________________________________________________________________________

    List<HorarioDTO> listarHorariosMedico(int codigoMedico) throws  Exception;
    //________________________________________________________________________________________________________________

    void enviarLinkRecuperacion(String email)throws Exception;
    //________________________________________________________________________________________________________________

}
