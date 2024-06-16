package co.uniquindio.clinicaLaBienAmada.servicios.impl;

import co.uniquindio.clinicaLaBienAmada.dto.EmailDTO;
import co.uniquindio.clinicaLaBienAmada.dto.HorarioDTO;
import co.uniquindio.clinicaLaBienAmada.dto.paciente.ItemMedicoPacienteDTO;
import co.uniquindio.clinicaLaBienAmada.model.*;
import co.uniquindio.clinicaLaBienAmada.repositorios.CuentaRepo;
import co.uniquindio.clinicaLaBienAmada.repositorios.HorarioRepo;
import co.uniquindio.clinicaLaBienAmada.repositorios.MedicoRepo;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.ClinicaServicio;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.EmailServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClinicaServicioImpl implements ClinicaServicio {

    /**
     * Acceso a los repositorios apra realizar consultas
     */
    private final MedicoRepo medicoRepo;
    private  final HorarioRepo horarioRepo;
    private final CuentaRepo cuentaRepo;
    private final EmailServicio emailServicio;
    //________________________________________________________________________________________________________________


    /**
     * Retorna una lista de todas las ciudades disponibles en la aplicación.
     *
     * @return Una lista de objetos `Ciudad` que representan todas las ciudades disponibles.
     */
    @Override
    public List<Ciudad> listarCiudades() {
        return List.of(Ciudad.values());
    }
    //________________________________________________________________________________________________________________

    /**
     * Retorna una lista de todas las especialidades médicas disponibles en la aplicación.
     *
     * @return Una lista de objetos `Especialidad` que representan todas las especialidades disponibles.
     */
    @Override
    public List<Especialidad> listarEspecialidades() {
        return List.of( Especialidad.values() );
    }
    //________________________________________________________________________________________________________________

    /**
     * Retorna una lista de todos los tipos de sangre disponibles en la aplicación.
     *
     * @return Una lista de objetos `TipoDeSangre` que representan todos los tipos de sangre disponibles.
     */
    @Override
    public List<TipoDeSangre> listarTiposSangre() {
        return List.of( TipoDeSangre.values() );
    }
    //________________________________________________________________________________________________________________

    /**
     * Retorna una lista de todas las Entidades Promotoras de Salud (EPS) disponibles en la aplicación.
     *
     * @return Una lista de objetos `Eps` que representan todas las EPS disponibles.
     */
    @Override
    public List<Eps> listarEps() {
        return List.of( Eps.values() );
    }
    //________________________________________________________________________________________________________________

    /**
     * Retorna una lista de médicos que pertenecen a una especialidad específica,
     * junto con sus detalles básicos y horarios de disponibilidad.
     *
     * @param especialidad La especialidad médica por la cual se desea filtrar los médicos.
     * @return Una lista de objetos `ItemMedicoPacienteDTO` que contienen los detalles de los médicos
     *         que tienen la especialidad especificada, incluyendo sus horarios de disponibilidad.
     * @throws Exception Si ocurre algún error al intentar recuperar la lista de médicos por especialidad.
     */
    @Override
    public List<ItemMedicoPacienteDTO> listarMedicoPorEspecialidad(Especialidad especialidad) throws Exception {

        List<Medico> listaMedicoEspecialidad = medicoRepo.findAllByEspecialidad(especialidad);
        List<ItemMedicoPacienteDTO> respuesta = new ArrayList<>();

        for (Medico m : listaMedicoEspecialidad) {
            List<HorarioDTO> horariosDTO = m.getHorarios().stream()
                    .map(horario -> new HorarioDTO(horario.getDia(), horario.getHoraInicio(), horario.getHoraFin()))
                    .collect(Collectors.toList());

            respuesta.add(new ItemMedicoPacienteDTO(
                    m.getCodigo(),
                    m.getNombre(),
                    m.getCorreo(),
                    horariosDTO  // Asignar la lista de horarios al médico
            ));
        }

        return respuesta;
    }
    //________________________________________________________________________________________________________________

    /**
     * Retorna una lista de los horarios de disponibilidad de un médico específico.
     *
     * @param codigoMedico El código del médico para el cual se desean obtener los horarios de disponibilidad.
     * @return Una lista de objetos `HorarioDTO` que contienen los horarios de disponibilidad del médico.
     * @throws Exception Si no se encuentran horarios de disponibilidad para el médico especificado.
     */
    @Override
    public List<HorarioDTO> listarHorariosMedico(int codigoMedico) throws Exception {

        List<Horario> horarioEncontrado = horarioRepo.findByMedicoCodigo(codigoMedico);

        List<HorarioDTO> respuesta = new ArrayList<>();

        for (Horario h : horarioEncontrado){
            respuesta.add(new HorarioDTO(
                    h.getDia(),
                    h.getHoraInicio(),
                    h.getHoraFin()
            ));
        }


        return respuesta;
    }
    //________________________________________________________________________________________________________________

    /**
     * Envía un correo electrónico con un enlace de recuperación de contraseña
     * a la dirección de correo especificada.
     *
     * @param email La dirección de correo electrónico a la cual se enviará el enlace de recuperación.
     * @throws Exception Si la dirección de correo electrónico no está asociada a ninguna cuenta en el sistema.
     */
    @Override
    public void enviarLinkRecuperacion(String email) throws Exception {

        Optional<Cuenta> optionalCuenta = cuentaRepo.findByCorreo(email);

        if (optionalCuenta.isEmpty()){
            throw new Exception("No existe la cuenta con correo" + email);
        }

        LocalDateTime fecha = LocalDateTime.now();
        String parametro = Base64.getEncoder().encodeToString((optionalCuenta.get().getCodigo()+
                ";"+fecha).getBytes());

        emailServicio.enviarCorreo(new EmailDTO(
                optionalCuenta.get().getCorreo(),
                "Recuperacion de cuenta",
                "Para la recuperacion de la cuenta ingrese al siguiente link https://XXXX" +
                        "/recuperar-password/" + parametro

        ));
    }
    //________________________________________________________________________________________________________________

}
