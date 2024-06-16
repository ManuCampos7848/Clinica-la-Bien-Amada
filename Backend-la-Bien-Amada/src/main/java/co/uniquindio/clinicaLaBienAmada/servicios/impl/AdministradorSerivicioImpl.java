package co.uniquindio.clinicaLaBienAmada.servicios.impl;

import co.uniquindio.clinicaLaBienAmada.dto.*;
import co.uniquindio.clinicaLaBienAmada.dto.paciente.ItemPacienteDTO;
import co.uniquindio.clinicaLaBienAmada.model.*;
import co.uniquindio.clinicaLaBienAmada.repositorios.*;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.AdmnistradorServicio;
import co.uniquindio.clinicaLaBienAmada.dto.admin.DetalleMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.admin.ItemMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.admin.RegistroMedicoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdministradorSerivicioImpl implements AdmnistradorServicio {

    /**
     * Acceso a los repositorios apra realizar consultas
     */
    private final MedicoRepo medicoRepo;
    private final PacienteRepo pacienteRepo;
    private final PQRSRepo pqrsRepo;
    private final CitaRepo citaRepo;
    private final MensajeRepo mensajeRepo;
    private final CuentaRepo cuentaRepo;
    private final HorarioRepo horarioRepo;
    //________________________________________________________________________________________________________________

    /**
     * Crea un nuevo registro de médico con la información proporcionada en el DTO de registro.
     *
     * @param medicoDTO DTO que contiene los datos del médico a registrar.
     * @return El código generado para el nuevo médico registrado.
     * @throws Exception Si la cédula o correo del médico ya están en uso por otro registro.
     */
    @Override
    public int crearMedico(RegistroMedicoDTO medicoDTO) throws Exception {

        if (estaRepetidaCedula(medicoDTO.cedula())) {
            throw new Exception("La cedula" + medicoDTO.cedula() + " ya esta en uso");
        }

        if (estaRepetidoCorreo(medicoDTO.correo())) {
            throw new Exception("El correo" + medicoDTO.correo() + " ya esta en uso");
        }

        Medico medico = new Medico();

        medico.setCedula(medicoDTO.cedula());
        medico.setTelefono(medicoDTO.telefono());
        medico.setNombre(medicoDTO.nombre());
        medico.setEspecialidad(medicoDTO.especialidad());
        medico.setCiudad(medicoDTO.ciudad());
        medico.setCorreo(medicoDTO.correo());
        medico.setPassword(medicoDTO.password());
        medico.setUrlFoto(medicoDTO.URLFoto());
        //medico.setEstado(EstadoUsuario.ACTIVO);
        medico.setEstado(true);


        //Solo falta que esto quede guardado y e profe lo vea
        Medico medicoNuevo = medicoRepo.save(medico);

        //c asignarHorariosMedico(medicoNuevo, medicoDTO.horarios());

        return medicoNuevo.getCodigo();
    }

    /**
     * Metodo que permite saber si una cedula esta repetida
     * @param cedula
     * @return
     */
    private boolean estaRepetidaCedula(String cedula) {
        return medicoRepo.findByCedula(cedula) != null;
    }
    //________________________________________________________________________________________________________________

    /**
     * Metodo que permite saber si un correo esta repetida
     * @param correo
     * @return
     */
    private boolean estaRepetidoCorreo(String correo) {
        return medicoRepo.findByCorreo(correo) != null;
    }
    //________________________________________________________________________________________________________________

    /**
     * Actualiza la información de un médico existente con los datos proporcionados en el DTO de detalle.
     *
     * @param medicoDTO DTO que contiene los datos actualizados del médico.
     * @return El código del médico actualizado.
     * @throws Exception Si el código del médico no existe en la base de datos.
     */
    @Override
    public int actualizarMedico(DetalleMedicoDTO medicoDTO) throws Exception {

        Optional<Medico> buscado = medicoRepo.findById(medicoDTO.codigo());

        if (buscado.isEmpty()) {
            throw new Exception("El código " + medicoDTO.cedula() + " no existe");
        }

        Medico medico = buscado.get();
        medico.setCedula(medicoDTO.cedula());
        medico.setTelefono(medicoDTO.telefono());
        medico.setNombre(medicoDTO.nombre());
        medico.setEspecialidad(medicoDTO.especialidad());
        medico.setCiudad(medicoDTO.ciudad());
        medico.setCorreo(medicoDTO.correo());
        medico.setUrlFoto(medicoDTO.urlFoto());

        Medico medicoNuevo = medicoRepo.save(medico);

        return medicoNuevo.getCodigo();
    }
    //________________________________________________________________________________________________________________

    /**
     * Cambia el estado de un médico a inactivo, basado en su código.
     *
     * @param codigo El código del médico que se desea eliminar.
     * @return `true` si el médico se desactivó correctamente, de lo contrario lanza una excepción.
     * @throws Exception Si el código del médico no existe en la base de datos.
     */
    @Override
    public boolean eliminarMedico(int codigo) throws Exception {

        Optional<Medico> buscado = medicoRepo.findById(codigo); // SELECT * FROM MEDICO WHERE CODIGO = CODIGO

        if (buscado.isEmpty()) {
            throw new Exception("El codigo" + codigo + " no existe mi fai");
        }
        // medicoRepo.delete(buscado.get()); //delete from medico where codigo = codigo

        Medico obtenido = buscado.get();

        obtenido.setEstado(false);
        medicoRepo.save(obtenido);
       // medicoRepo.delete(obtenido);


        return true;
    }
    //________________________________________________________________________________________________________________

    /**
     * Lista todos los médicos registrados y activos en el sistema.
     *
     * @return Una lista de objetos `ItemMedicoDTO` que representan los médicos activos.
     * @throws Exception Si no hay médicos registrados o activos en el sistema.
     */
    @Override
    public List<ItemMedicoDTO> listarMedicos() throws Exception {

        List<Medico> medicos = medicoRepo.findAll();
        List<ItemMedicoDTO> respuesta = new ArrayList<>();


        if (medicos.isEmpty()) {
            throw new Exception(("No hay medicos registrados"));
        }

        for (Medico medico : medicos) {
            if (medico.isEstado()) {
                respuesta.add(new ItemMedicoDTO(
                        medico.getCodigo(),
                        medico.getCedula(),
                        medico.getNombre(),
                        medico.getUrlFoto(),
                        medico.getEspecialidad()
                ));
            }

        }

        return respuesta;
    }
    //________________________________________________________________________________________________________________

    /**
     * Obtiene los detalles de un médico específico basado en su código.
     *
     * @param codigo El código del médico del cual se desean obtener los detalles.
     * @return Un objeto `DetalleMedicoDTO` con los detalles del médico solicitado.
     * @throws Exception Si el código del médico no existe en la base de datos.
     */
    @Override
    public DetalleMedicoDTO obtenerMedico(int codigo) throws Exception {

        Optional<Medico> buscado = medicoRepo.findById(codigo); // SELECT * FROM MEDICO WHERE CODIGO = CODIGO


        if (buscado.isEmpty()) {
            throw new Exception("El codigo" + codigo + " no existe mi fai");
        }

        Medico obtenido = buscado.get();

        List<HorarioDTO> horariosDTO = obtenido.getHorarios().stream()
                .map(horario -> new HorarioDTO(horario.getDia(), horario.getHoraInicio(), horario.getHoraFin()))
                .collect(Collectors.toList());

        DetalleMedicoDTO detalleMedicoDTO = new DetalleMedicoDTO(
                obtenido.getCodigo(),
                obtenido.getNombre(),
                obtenido.getCedula(),
                obtenido.getCiudad(),
                obtenido.getEspecialidad(),
                obtenido.getTelefono(),
                obtenido.getCorreo(),
                obtenido.getUrlFoto(),
                horariosDTO

        );
        return detalleMedicoDTO;
    }
    //________________________________________________________________________________________________________________

    /**
     * Lista todas las PQRS (Peticiones, Quejas, Reclamos, Sugerencias) registradas en el sistema.
     *
     * @return Una lista de objetos `ItemPQRSDTO` que contienen información resumida de cada PQRS.
     * @throws Exception Si no hay PQRS registradas en el sistema.
     */
    @Override
    public List<ItemPQRSDTO> listarPQRS() throws Exception {

        List<Pqrs> listaPqrs = pqrsRepo.findAll();

        List<ItemPQRSDTO> respuesta = new ArrayList<>();

        for (Pqrs p : listaPqrs) {
            respuesta.add(new ItemPQRSDTO(
                    p.getCodigo(),
                    p.getEstadoPQRS(),
                    p.getMotivo(),
                    p.getFecha_Creacion(),
                    p.getCita().getPaciente().getNombre()
            ));
        }

        return respuesta;
    }
    //________________________________________________________________________________________________________________

    /**
     * Obtiene los detalles completos de un PQRS específico basado en su código.
     *
     * @param codigo El código del PQRS del cual se desean obtener los detalles.
     * @return Un objeto `DetallePQRSDTO` con los detalles completos del PQRS solicitado.
     * @throws Exception Si el código del PQRS no existe en la base de datos.
     */
    @Override
    public DetallePQRSDTO verDetallePQRS(int codigo) throws Exception {

        Optional<Pqrs> opcional = pqrsRepo.findById(codigo);

        if (opcional.isEmpty()) {
            throw new Exception("El código " + codigo + " no está asociado a ningún PQRS");
        }

        Pqrs pqrs = opcional.get();

        return new DetallePQRSDTO(
                pqrs.getCodigo(),
                pqrs.getEstadoPQRS(),
                pqrs.getMotivo(),
                pqrs.getCita().getPaciente().getNombre(),
                pqrs.getCita().getMedico().getNombre(),
                pqrs.getCita().getMedico().getEspecialidad(),
                pqrs.getFecha_Creacion(),
                new ArrayList<>()
        );
    }
    //________________________________________________________________________________________________________________

    /**
     * Responde a un PQRS específico con un mensaje de respuesta proporcionado en el DTO de registro.
     *
     * @param registroRespuestaDTO DTO que contiene el código del PQRS y el mensaje de respuesta.
     * @return El código generado para la respuesta al PQRS.
     * @throws Exception Si el código del PQRS o la cuenta asociada no existen en la base de datos.
     */
    @Override
    public int responderPQRS(RegistroRespuestaDTO registroRespuestaDTO) throws Exception {

        //Obetener el PQRS
        Optional<Pqrs> opcionalPqrs = pqrsRepo.findById(registroRespuestaDTO.codigoPQRS()); // SELECT * FROM MEDICO WHERE CODIGO = CODIGO
        Optional<Cuenta> cuentaEncontrada = cuentaRepo.findById(registroRespuestaDTO.codigoCuenta());

        if (opcionalPqrs.isEmpty()) {
            throw new Exception("El codigo" + registroRespuestaDTO.codigoPQRS() + " no esta asociado a ningun PQRS");
        }

        //Obtener la cuenta / SELECT * FROM MEDICO WHERE CODIGO = CODIGO

        if (cuentaEncontrada.isEmpty()) {
            throw new Exception("El codigo" + registroRespuestaDTO.codigoCuenta() + " no esta asociado a ningun PQRS");
        }


        Mensaje mensaje = new Mensaje();
        mensaje.setFechaCreacion(LocalDate.now());
        mensaje.setMensaje(registroRespuestaDTO.mensaje());
        mensaje.setPqrs(opcionalPqrs.get());
        mensaje.setCuenta(cuentaEncontrada.get());

        return mensajeRepo.save(mensaje).getCodigo();
    }
    //________________________________________________________________________________________________________________

    /**
     * Cambia el estado de un PQRS específico a través de su código.
     *
     * @param codigoPQRS El código del PQRS cuyo estado se desea cambiar.
     * @param estadoPQRS El nuevo estado del PQRS.
     * @return El código del PQRS cuyo estado fue modificado.
     * @throws Exception Si el código del PQRS no existe en la base de datos.
     */
    @Override
    public int cambiarEstadoPQRS(int codigoPQRS, EstadoPQRS estadoPQRS) throws Exception {

        Optional<Pqrs> opcional = pqrsRepo.findById(codigoPQRS); // SELECT * FROM MEDICO WHERE CODIGO = CODIGO

        if (opcional.isEmpty()) {
            throw new Exception("El codigo" + codigoPQRS + " no esta asociado a ningun PQRS");
        }

        Pqrs pqrs = opcional.get();
        pqrs.setEstadoPQRS(estadoPQRS);

        pqrsRepo.save(pqrs);

        return pqrs.getCodigo();
    }
    //________________________________________________________________________________________________________________

    /**
     * Lista todas las citas registradas en el sistema.
     *
     * @return Una lista de objetos `ItemCitaDTO` que contienen información resumida de cada cita.
     * @throws Exception Si no hay citas registradas en el sistema.
     */
    @Override
    public List<ItemCitaDTO> listarCitas() throws Exception {

        List<Cita> listaCitas = citaRepo.findAll();

        List<ItemCitaDTO> respuesta = new ArrayList<>();

        for (Cita c : listaCitas) {
            respuesta.add(new ItemCitaDTO(

                    c.getCodigo(),
                    c.getPaciente().getCedula(),
                    c.getPaciente().getNombre(),
                    c.getMedico().getNombre(),
                    c.getMedico().getEspecialidad(),
                    c.getEstadoCita(),
                    c.getFechaCita(),
                    c.getHoraCita()

            ));


        }

        return respuesta;
    }
    //________________________________________________________________________________________________________________

    /**
     * Lista todos los pacientes registrados en el sistema.
     *
     * @return Una lista de objetos `ItemPacienteDTO` que contienen información resumida de cada paciente.
     * @throws Exception Si no hay pacientes registrados en el sistema.
     */
    @Override
    public List<ItemPacienteDTO> listarTodos() throws Exception {

        List<Paciente> pacientes = pacienteRepo.findAll();
        List<ItemPacienteDTO> respuesta = new ArrayList<>();

        for (Paciente paciente : pacientes) {
            respuesta.add(new ItemPacienteDTO(paciente.getCodigo(), paciente.getCedula(),
                    paciente.getNombre(), paciente.getCiudad()));
        }

        return respuesta;
    }
    //________________________________________________________________________________________________________________
}
