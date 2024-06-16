package co.uniquindio.clinicaLaBienAmada.servicios.impl;

import co.uniquindio.clinicaLaBienAmada.dto.*;
import co.uniquindio.clinicaLaBienAmada.dto.medico.DetalleAtencionMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.paciente.*;
import co.uniquindio.clinicaLaBienAmada.model.*;
import co.uniquindio.clinicaLaBienAmada.repositorios.*;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.EmailServicio;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.PacienteServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

@Service
@RequiredArgsConstructor
public class    PacienteServicioImpl implements PacienteServicio {

    /**
     * Acceso a los repositorios apra realizar consultas
     */
    private final PacienteRepo pacienteRepo;
    private final CitaRepo citaRepo;
    private final AtencionRepo atencionRepo;
    private final MedicoRepo medicoRepo;
    private final PQRSRepo pqrsRepo;
    private final MensajeRepo mensajeRepo;
    private final CuentaRepo cuentaRepo;
    private final EmailServicio emailServicio;
    private final DiaLibreRepo diaLibreRepo;
    //________________________________________________________________________________________________________________

    /**
     * Registra un nuevo paciente en el sistema.
     *
     * @param registroPacienteDTO Datos del paciente a registrar.
     * @return El código del paciente registrado.
     * @throws Exception Si la cédula o el correo ya están en uso por otro paciente o médico.
     */
    @Override
    public int registrarse(RegistroPacienteDTO registroPacienteDTO) throws Exception {

        if(estaRepetidaCedula(registroPacienteDTO.cedula())){
            throw new Exception("La cedula o el correo ya se encuentran en uso");
        }
        if(estaRepetidoCorreoListaPacientes(registroPacienteDTO.correo())){
            throw new Exception("El Correo ya se encuentran en uso");
        }
        if(estaRepetidoCorreoListaMedicos(registroPacienteDTO.correo())){
            throw new Exception("El Correo ya se encuentran en uso");
        }


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(registroPacienteDTO.password());


        Paciente paciente = new Paciente();
        paciente.setCorreo(registroPacienteDTO.correo());
        paciente.setPassword(passwordEncriptada);

        paciente.setCedula(registroPacienteDTO.cedula());
        paciente.setNombre(registroPacienteDTO.nombre());
        paciente.setTelefono(registroPacienteDTO.telefono());
        paciente.setUrlFoto(registroPacienteDTO.urlFoto());
        paciente.setCiudad(registroPacienteDTO.ciudad());
        paciente.setEstado(true);

        paciente.setFechaNacimiento(registroPacienteDTO.fechaNacimiento());
        paciente.setAlergias(registroPacienteDTO.alergias());
        paciente.setEps(registroPacienteDTO.eps());
        paciente.setTipoDeSangre(registroPacienteDTO.tipoSangre());


        Paciente pacienteNuevo = pacienteRepo.save(paciente);

       emailServicio.enviarCorreo(new EmailDTO(
                paciente.getCorreo(),
                "Bienvenido a la Clinica la Bien Amada",
                "Felicidades, su registro ha sido exitoso. Bienvenido " + paciente.getNombre()
        ));


        return pacienteNuevo.getCodigo();
    }
    //________________________________________________________________________________________________________________


    /**
     * Lista el historial de atenciones asociadas a una cita específica.
     *
     * @param codigoCita El código único de la cita para la cual se desea obtener el historial de atenciones.
     * @return Una lista de objetos DetalleAtencionMedicoDTO que contienen el historial de atenciones.
     * @throws Exception Si no se encuentran atenciones asociadas a la cita especificada.
     */
    @Override
    public List<DetalleAtencionMedicoDTO> listarHistorialAtenciones(int codigoCita) throws Exception {

        List<Atencion> atenciones = atencionRepo.findAllByCita_Codigo(codigoCita);

        List<DetalleAtencionMedicoDTO> respuesta = new ArrayList<>();


        for (Atencion detalles : atenciones) {
            respuesta.add(new DetalleAtencionMedicoDTO(
                    detalles.getCita().getCodigo(),
                    detalles.getCita().getPaciente().getNombre(),
                    detalles.getCita().getMedico().getNombre(),
                    detalles.getCita().getMedico().getCodigo(),
                    detalles.getCita().getMedico().getEspecialidad(),
                    detalles.getCita().getFechaCita(),
                    detalles.getTratamiento(),
                    detalles.getNotasMedicas(),
                    detalles.getDiagnostico()
            ));
        }


        return respuesta;
    }
    //________________________________________________________________________________________________________________


    /**
     * Retorna el detalle de una atención asociada a una cita específica.
     *
     * @param codigoCita El código único de la cita para la cual se desea obtener el detalle de la atención.
     * @return Un objeto DetalleAtencionMedicoDTO que contiene el detalle de la atención.
     * @throws Exception Si no se encuentra ninguna atención asociada a la cita especificada.
     */
    @Override
    public DetalleAtencionMedicoDTO verDetalleAtencion(int codigoCita) throws Exception {

        Optional<Atencion> atencionEncontrada = atencionRepo.findByCitaCodigo(codigoCita);

        if (atencionEncontrada.isEmpty()) {
            throw new Exception("No se pudo encontrar la cita dada el codigo, o tal vez no existe");
        }

        Atencion atencion = atencionEncontrada.get();

        return new DetalleAtencionMedicoDTO(
                atencion.getCita().getCodigo(),
                atencion.getCita().getPaciente().getNombre(),
                atencion.getCita().getMedico().getNombre(),
                atencion.getCita().getMedico().getCodigo(),
                atencion.getCita().getMedico().getEspecialidad(),
                atencion.getCita().getFechaCita(),
                atencion.getTratamiento(),
                atencion.getNotasMedicas(),
                atencion.getDiagnostico()
        );
    }
    //________________________________________________________________________________________________________________

    /**
     * Lista las citas completadas de un paciente.
     *
     * @param codigoPaciente El código del paciente para el cual se desean listar las citas completadas.
     * @return Una lista de objetos ItemCitaDTO que contienen las citas completadas del paciente.
     * @throws Exception Si no se encuentran citas completadas para el paciente especificado.
     */
    @Override
    public List<ItemCitaDTO> listarCitasCompletadasPaciente(int codigoPaciente) throws Exception {

        List<Cita> citasEncontradas = citaRepo.findAllByPacienteCodigo(codigoPaciente);

        List<ItemCitaDTO> citas = new ArrayList<>();

        for (Cita cita : citasEncontradas) {
            if (cita.getEstadoCita().equals(EstadoCita.COMPLETADA)) {
                citas.add(new ItemCitaDTO(
                        cita.getCodigo(),
                        cita.getPaciente().getCedula(),
                        cita.getPaciente().getNombre(),
                        cita.getMedico().getNombre(),
                        cita.getMedico().getEspecialidad(),
                        cita.getEstadoCita(),
                        cita.getFechaCita(),
                        cita.getHoraCita()
                ));
            }
        }

        return citas;
    }
    //________________________________________________________________________________________________________________


    /**
     * Agenda una nueva cita para un paciente con un médico especificado en una fecha y hora dadas.
     *
     * @param registroCitaDTO Datos de la cita a agendar.
     * @return El código de la nueva cita agendada.
     * @throws Exception Si el día de la cita no está disponible para agendar, si ya existe una cita programada para la misma fecha y hora con el mismo médico, si el paciente tiene más de 3 citas programadas, o si ya tiene una cita agendada para la misma fecha.
     */
    @Override
    public int agendarCita(RegistroCitaDTO registroCitaDTO) throws Exception {
        List<Cita> citasAgendadas = citaRepo.findAllByPacienteCodigo(registroCitaDTO.codigoPaciente());

        // Verificar si el paciente existe
        Optional<Paciente> pacienteObtenido = pacienteRepo.findById(registroCitaDTO.codigoPaciente());
        if (!pacienteObtenido.isPresent()) {
            throw new Exception("Paciente no encontrado");
        }

        // Verificar si el médico existe
        Optional<Medico> medicoObtenido = medicoRepo.findById(registroCitaDTO.codigoMedico());
        if (!medicoObtenido.isPresent()) {
            throw new Exception("Médico no encontrado");
        }

        // Verificar si el médico tiene el día libre
        Optional<DiaLibre> diasLibresMedico = diaLibreRepo.findByMedicoCodigo(medicoObtenido.get().getCodigo());
        if (diasLibresMedico.isPresent() && diasLibresMedico.get().getDia().equals(registroCitaDTO.fechaCita())) {
            throw new Exception("Este día no está disponible para agendar citas");
        }

        // Verificar si ya hay una cita programada para ese médico y fecha
        Optional<Cita> verificarHoraCita = citaRepo.findByMedicoCodigoAndFechaCita(registroCitaDTO.codigoMedico(), registroCitaDTO.fechaCita());
        if (verificarHoraCita.isPresent() && verificarHoraCita.get().getEstadoCita().equals(EstadoCita.PROGRAMADA)
                && verificarHoraCita.get().getHoraCita().equals(registroCitaDTO.horaCita())) {
            throw new Exception("Este horario ya está ocupado por otra cita");
        }

        if (citasAgendadas.isEmpty()) {
            return crearCita(registroCitaDTO, pacienteObtenido.get(), medicoObtenido.get());
        } else {
            int contProgramadas = (int) citasAgendadas.stream()
                    .filter(cita -> EstadoCita.PROGRAMADA.equals(cita.getEstadoCita()))
                    .count();

            if (contProgramadas >= 3) {
                throw new Exception("Tiene más de 3 citas programadas");
            }

            for (Cita c : citasAgendadas) {
                if (c.getFechaCita().equals(registroCitaDTO.fechaCita())) {
                    throw new Exception("Este día ya tiene una cita agendada");
                }
            }

            return crearCita(registroCitaDTO, pacienteObtenido.get(), medicoObtenido.get());
        }
    }

    private int crearCita(RegistroCitaDTO registroCitaDTO, Paciente paciente, Medico medico) throws Exception {
        Random random = new Random();
        int numeroAleatorio = random.nextInt(5);
        Sede sede = Sede.values()[numeroAleatorio];

        Cita cita = new Cita();
        cita.setFechaCita(registroCitaDTO.fechaCita());
        cita.setHoraCita(registroCitaDTO.horaCita());
        cita.setMotivo(registroCitaDTO.motivo());
        cita.setEstadoCita(registroCitaDTO.estadoCita());
        cita.setSede(sede);
        cita.setPaciente(paciente);
        cita.setMedico(medico);

        Cita citaNueva = citaRepo.save(cita);

        LocalDate fechaCita = citaNueva.getFechaCita();
        Period periodoHastaCita = Period.between(LocalDate.now(), fechaCita);
        int diasFaltantes = periodoHastaCita.getDays();

        emailServicio.enviarCorreo(new EmailDTO(
                paciente.getCorreo(),
                "Se ha agendado una nueva cita",
                "La cita se ha agendado con el médico " + cita.getMedico().getNombre() + " el día " + cita.getFechaCita()
        ));

        emailServicio.enviarCorreo(new EmailDTO(
                paciente.getCorreo(),
                "Faltan " + diasFaltantes + " días para su cita",
                "Faltan " + diasFaltantes + " días para la realización de su cita médica. Recuerde llevar su documento a la hora de asistir a la cita al igual que un tapabocas."
        ));

        return citaNueva.getCodigo();
    }

    /**
     * Crea un nuevo PQRS asociado a una cita específica.
     *
     * @param registroPQRSDTO Datos del PQRS a crear.
     * @return El código del nuevo PQRS creado.
     * @throws Exception Si no se encuentra la cita asociada al código especificado.
     */
    @Override
    public int crearPQRS(RegistroPQRSDTO registroPQRSDTO) throws Exception {

        Optional<Cita> citaEncontrada = citaRepo.findById(registroPQRSDTO.codigoCita());

        Pqrs pqrs = new Pqrs();


        pqrs.setMotivo(registroPQRSDTO.motivo());
        pqrs.setCita(citaEncontrada.get());
        pqrs.getCita().getPaciente().setCodigo(registroPQRSDTO.codigoPaciente());
        pqrs.setEstadoPQRS(registroPQRSDTO.estadoPQRS());
        pqrs.setFecha_Creacion(registroPQRSDTO.fechaCreacion());
        pqrs.setTipo(registroPQRSDTO.tipo());

        Pqrs pqrsNueva = pqrsRepo.save(pqrs);

        return pqrsNueva.getCodigo();
    }
    //________________________________________________________________________________________________________________

    /**
     * Retorna el detalle completo de un paciente dado su código.
     *
     * @param codigo El código del paciente para el cual se desea obtener el detalle.
     * @return Un objeto DetallePacienteDTO que contiene el detalle del paciente.
     * @throws Exception Si no se encuentra ningún paciente con el código especificado.
     */
    @Override
    public DetallePacienteDTO verDetallePaciente(int codigo) throws Exception {

        // EL OPCIONAL VA A LA BASE DE DATOS PARA HACER EL IF(PACIENTE -> EXISTE)
        Optional<Paciente> pacienteBuscado = pacienteRepo.findById(codigo);

        if (pacienteBuscado.isEmpty()){
            throw new Exception("El paciente que se intenta buscar no existe");
        }

        Paciente paciente = pacienteBuscado.get();

        return new DetallePacienteDTO(paciente.getCodigo(), paciente.getCedula(),
                paciente.getNombre(), paciente.getTelefono(), paciente.getUrlFoto(), paciente.getCiudad(),
                paciente.getFechaNacimiento(), paciente.getAlergias(), paciente.getEps(),
                paciente.getTipoDeSangre(), paciente.getCorreo());
    }
    //________________________________________________________________________________________________________________

    /**
     * Lista los PQRS asociados a un paciente específico.
     *
     * @param codigoPciente El código del paciente para el cual se desean listar los PQRS.
     * @return Una lista de objetos ItemPQRSDTO que contienen los PQRS asociados al paciente.
     * @throws Exception Si el paciente no tiene ningún PQRS asociado.
     */
    @Override
    public List<ItemPQRSDTO> listarPQRSPciente(int codigoPciente) throws Exception {

        if(existenciaPQRS(codigoPciente).isEmpty()){
            throw new Exception("El paciente con codigo " + codigoPciente + " no tiene PQRS");
        }

        List<Pqrs> pqrs = pqrsRepo.findAllByCita_Paciente_Codigo(codigoPciente);
        List<ItemPQRSDTO> respuesta = new ArrayList<>();

        for (Pqrs p : pqrs){
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
     * Lista todas las citas asociadas a un paciente específico.
     *
     * @param codigoPaciente El código del paciente para el cual se desean listar las citas.
     * @return Una lista de objetos ItemCitaDTO que contienen las citas asociadas al paciente.
     * @throws Exception Si no se encuentran citas asociadas al paciente especificado.
     */
    @Override
    public List<ItemCitaDTO> listarCitasPaciente(int codigoPaciente) throws Exception {

        List<Cita> citasEncontradas = citaRepo.findAllByPacienteCodigo(codigoPaciente);

        List<ItemCitaDTO> citas = new ArrayList<>();

        for (Cita cita : citasEncontradas){
            citas.add(new ItemCitaDTO(
                    cita.getCodigo(),
                    cita.getPaciente().getCedula(),
                    cita.getPaciente().getNombre(),
                    cita.getMedico().getNombre(),
                    cita.getMedico().getEspecialidad(),
                    cita.getEstadoCita(),
                    cita.getFechaCita(),
                    cita.getHoraCita()
            ));
        }

        return citas;
    }
    //________________________________________________________________________________________________________________


    /**
     * Retorna el detalle de una cita dado su código.
     *
     * @param codigoCita El código único de la cita.
     * @return Un objeto DetalleCitaDTO que contiene los detalles de la cita.
     * @throws Exception Si no se encuentra ninguna cita con el código proporcionado.
     */
    @Override
    public DetalleCitaDTO verDetalleCita(int codigoCita) throws Exception {

        Optional<Cita> citaEncontrada = citaRepo.findById(codigoCita);

        if(citaEncontrada.isEmpty()){
            throw new Exception("No existe tal PQRS con ese codigo");
        }

        Cita cita = citaEncontrada.get();

        return new DetalleCitaDTO(
                cita.getCodigo(),
                cita.getPaciente().getCedula(),
                cita.getPaciente().getNombre(),
                cita.getMedico().getNombre(),
                cita.getMedico().getCodigo(),
                cita.getMedico().getEspecialidad(),
                cita.getEstadoCita(),
                cita.getMotivo(),
                cita.getSede(),
                cita.getFechaCita()
        );
    }
    //________________________________________________________________________________________________________________

    /**
     * Filtra las citas de un paciente por fecha específica.
     *
     * @param codigoPaciente El código del paciente para el cual se buscan las citas.
     * @param fecha La fecha específica para filtrar las citas.
     * @return Una lista de objetos FiltroBusquedaDTO que contienen las citas filtradas.
     * @throws Exception Si no se encuentran citas para el paciente en la fecha especificada.
     */
    @Override
    public List<FiltroBusquedaDTO> filtrarCitasPorFecha(int codigoPaciente, LocalDate fecha) throws Exception {

        List<Cita> citaEcontradaPorFecha = citaRepo.findAllByFechaCitaAndPacienteCodigo(fecha,codigoPaciente);

        if(citaEcontradaPorFecha.isEmpty()){
            throw new Exception("\n No hay tal lista de citas por la fecha introducida.");
        }

        List<FiltroBusquedaDTO> citas = new ArrayList<>();

        for(Cita c : citaEcontradaPorFecha){
            citas.add(new FiltroBusquedaDTO(
                    c.getCodigo(),
                    c.getMedico().getCodigo(),
                    c.getMedico().getNombre(),
                    c.getMotivo(),
                    c.getFechaCita()
            ));
        }

        return citas;
    }
    //________________________________________________________________________________________________________________

    /**
     * Filtra las atenciones de un paciente por fecha específica.
     *
     * @param codigoPaciente El código del paciente para el cual se buscan las atenciones.
     * @param fecha La fecha específica para filtrar las atenciones.
     * @return Una lista de objetos DetalleAtencionMedicoDTO que contienen las atenciones filtradas.
     * @throws Exception Si no se encuentran atenciones para el paciente en la fecha especificada.
     */
    @Override
    public List<DetalleAtencionMedicoDTO> filtrarAtencionesPorFecha(int codigoPaciente, LocalDate fecha) throws Exception {

       List<Atencion> atenciones = atencionRepo.findAllByCita_FechaCitaAndCita_Paciente_Codigo(fecha, codigoPaciente);

        if(atenciones.isEmpty()){
            throw new Exception("\n No hay tal lista de atenciones por la fecha introducida.");
        }

        List<DetalleAtencionMedicoDTO> respuesta = new ArrayList<>();

        for (Atencion detalles : atenciones) {
            respuesta.add(new DetalleAtencionMedicoDTO(
                    detalles.getCita().getCodigo(),
                    detalles.getCita().getPaciente().getNombre(),
                    detalles.getCita().getMedico().getNombre(),
                    detalles.getCita().getMedico().getCodigo(),
                    detalles.getCita().getMedico().getEspecialidad(),
                    detalles.getCita().getFechaCita(),
                    detalles.getTratamiento(),
                    detalles.getNotasMedicas(),
                    detalles.getDiagnostico()
            ));
        }

        return respuesta;
    }
    //________________________________________________________________________________________________________________

    /**
     * Filtra las atenciones de un paciente por código de médico.
     *
     * @param codigoPaciente El código del paciente para el cual se buscan las atenciones.
     * @param codigoMedico El código del médico para filtrar las atenciones.
     * @return Una lista de objetos DetalleAtencionMedicoDTO que contienen las atenciones filtradas.
     * @throws Exception Si no se encuentran atenciones para el paciente y médico especificados.
     */
    @Override
    public List<DetalleAtencionMedicoDTO> filtrarAtencionesPorMedico(int codigoPaciente, int codigoMedico) throws Exception {

        List<Atencion> atenciones = atencionRepo.findAllByCita_Paciente_CodigoAndCita_Medico_Codigo(codigoPaciente, codigoMedico);

        if(atenciones.isEmpty()){
            throw new Exception("\n No hay tal lista de atenciones por el codigo del medico introducido.");
        }

        List<DetalleAtencionMedicoDTO> respuesta = new ArrayList<>();

        for (Atencion detalles : atenciones) {
            respuesta.add(new DetalleAtencionMedicoDTO(
                    detalles.getCita().getCodigo(),
                    detalles.getCita().getPaciente().getNombre(),
                    detalles.getCita().getMedico().getNombre(),
                    detalles.getCita().getMedico().getCodigo(),
                    detalles.getCita().getMedico().getEspecialidad(),
                    detalles.getCita().getFechaCita(),
                    detalles.getTratamiento(),
                    detalles.getNotasMedicas(),
                    detalles.getDiagnostico()
            ));
        }

        return respuesta;
    }
    //________________________________________________________________________________________________________________

    /**
     * Filtra las atenciones de un paciente por código de médico.
     *
     * @param codigoPaciente El código del paciente para el cual se buscan las atenciones.
     * @param codigoMedico El código del médico para filtrar las atenciones.
     * @return Una lista de objetos DetalleAtencionMedicoDTO que contienen las atenciones filtradas.
     * @throws Exception Si no se encuentran atenciones para el paciente y médico especificados.
     */
    @Override
    public List<FiltroBusquedaDTO> filtrarCitasPorMedico(int codigoPaciente, int codigoMedico) throws Exception {

        List<Cita> citasEncontradasPorMedico = citaRepo.findAllByPacienteCodigoAndMedicoCodigo(codigoPaciente, codigoMedico);
        List<FiltroBusquedaDTO> citas = new ArrayList<>();

        if(citasEncontradasPorMedico.isEmpty()){
            throw new Exception("\n No hay tal lista de citas por el codigo del medico introducido introducida.");
        }

        for(Cita c : citasEncontradasPorMedico){
            citas.add(new FiltroBusquedaDTO(
                    c.getCodigo(),
                    c.getMedico().getCodigo(),
                    c.getMedico().getNombre(),
                    c.getMotivo(),
                    c.getFechaCita()
            ));
        }

        return citas;
    }
    //________________________________________________________________________________________________________________

    /**
     * Lista los mensajes asociados a un PQRS y un paciente específico.
     *
     * @param codigoPQRS El código del PQRS para el cual se buscan los mensajes.
     * @param codigoPaciente El código del paciente para el cual se buscan los mensajes.
     * @return Una lista de objetos RespuestaDTO que contienen los mensajes asociados.
     * @throws Exception Si no se encuentran mensajes para el PQRS y paciente especificados.
     */
    @Override
    public List<RespuestaDTO> listarMensajes(int codigoPQRS, int codigoPaciente) throws Exception {

        List<Mensaje> listaMensajes = mensajeRepo.findByPqrs_CodigoAndCuentaCodigo(codigoPQRS, codigoPaciente);

        List<RespuestaDTO> respuesta = new ArrayList<>();

        for (Mensaje m : listaMensajes){
            respuesta.add(new RespuestaDTO(
                    m.getCodigo(),
                    m.getMensaje(),
                    m.getCuenta().getCorreo(),
                    m.getFechaCreacion()
            ));
        };

        return respuesta;
    }
    //________________________________________________________________________________________________________________


    /**
     * Edita el perfil de un paciente con la información proporcionada.
     *
     * @param pacienteDTO Objeto DetallePacienteDTO con los nuevos detalles del paciente.
     * @return El código del paciente después de la edición.
     * @throws Exception Si no se encuentra ningún paciente con el código proporcionado.
     */

    @Override
    public int editarPerfil(DetallePacienteDTO pacienteDTO) throws Exception {

        Optional<Paciente> pacienteBuscado = pacienteRepo.findById(pacienteDTO.codigo());

        if(pacienteBuscado.isEmpty()){
            throw new Exception("El paciente buscado con codigo " + pacienteDTO.codigo() + " no existe");
        }

        Paciente paciente = pacienteBuscado.get();

        paciente.setCorreo(pacienteDTO.correo());
        paciente.setNombre(pacienteDTO.nombre());
        paciente.setTelefono(pacienteDTO.telefono());
        paciente.setCiudad(pacienteDTO.ciudad());
        paciente.setUrlFoto(pacienteDTO.urlFoto());

        paciente.setFechaNacimiento( pacienteDTO.fechaNacimiento() );
        paciente.setEps( pacienteDTO.eps() );
        paciente.setAlergias( pacienteDTO.alergias() );
        paciente.setTipoDeSangre( pacienteDTO.tipoSangre() );

        pacienteRepo.save(paciente);

        return paciente.getCodigo();
    }
    //________________________________________________________________________________________________________________

    /**
     * Elimina la cuenta de un paciente según su código.
     *
     * @param codigoPaciente El código del paciente cuya cuenta se desea eliminar.
     * @return true si la cuenta se eliminó correctamente, false de lo contrario.
     * @throws Exception Si no se encuentra ningún paciente con el código proporcionado.
     */
    @Override
    public boolean eliminarCuenta(int codigoPaciente) throws Exception {

        // EL OPCIONAL VA A LA BASE DE DATOS PARA HACER EL IF(PACIENTE -> EXISTE)
        Optional<Paciente> pacienteBuscado =pacienteRepo.findById(codigoPaciente);

        if( pacienteBuscado.isEmpty() ){
            throw new Exception("No existe el paciente con el código "+codigoPaciente);
        }

        Paciente buscado = pacienteBuscado.get();

        System.out.println("Se va a aliminar el paciente  " + buscado.getNombre());

        buscado.setEstado(false);
        //pacienteRepo.delete(pacienteBuscado.get());
        pacienteRepo.save( buscado );

        return true;

    }
    //________________________________________________________________________________________________________________


    /**
     * Retorna el detalle de un PQRS dado su código.
     *
     * @param codigo El código único del PQRS.
     * @return Un objeto DetallePQRSDTO que contiene los detalles del PQRS.
     * @throws Exception Si no se encuentra ningún PQRS con el código proporcionado.
     */
    @Override
    public DetallePQRSDTO verDetallePQRS(int codigo) throws Exception {

        Optional<Pqrs> pqrsEncontrada = pqrsRepo.findById(codigo);

        if(pqrsEncontrada.isEmpty()){
            throw new Exception("No existe tal PQRS con ese codigo");
        }

        Pqrs pqrs = pqrsEncontrada.get();

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
    //__________________________________________________________________________________________________


    //__________________________________________________________________________________________________

    //_________________________ Metodos de comprobacion ID y Correo desde el repo _________________________
    private boolean estaRepetidaCedula(String cedula) {
        return pacienteRepo.findByCedula(cedula) != null;
    }
    private boolean estaRepetidoCorreoListaPacientes(String email){
        return pacienteRepo.findByCorreo(email) != null;
    }
    private boolean estaRepetidoCorreoListaMedicos(String correo) {
        return medicoRepo.findByCorreo(correo) != null;
    }
    private List<Pqrs> existenciaPQRS(int codigoPciente) { return pqrsRepo.findAllByCita_Paciente_Codigo(codigoPciente);
    }
    //_____________________________________________________________________________________________________


    /**
     * Metodo que permite cambiar la contraseña
     * @param nuevaPasswordDTO
     * @throws Exception
     */
    @Override
    public void cambiarPassword(NuevaPasswordDTO nuevaPasswordDTO) throws Exception {

        String parametro = new String(Base64.getDecoder().decode(nuevaPasswordDTO.nuevaPassword()));
        String[] datos = parametro.split(";");
        int codigoCuenta = Integer.parseInt(datos[0]);
        LocalDateTime fecha = LocalDateTime.parse(datos[1]);

        if(fecha.plusMinutes(30).isBefore(LocalDateTime.now())){
            throw new Exception("El link de recuperacion ha expirado");
        }

        Cuenta cuenta = obtenerCuentaCodigo(nuevaPasswordDTO.codigoCuenta());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        cuenta.setPassword(passwordEncoder.encode(nuevaPasswordDTO.nuevaPassword()));
        cuentaRepo.save(cuenta);
    }
    //________________________________________________________________________________________________________________


    /**
     * Metodo que permite otner el codigo de la cuenta
     * @param codigoCuenta
     * @return
     */
    private Cuenta obtenerCuentaCodigo(int codigoCuenta) {

        Optional<Cuenta> cuenta = cuentaRepo.findById(codigoCuenta);

        return cuenta.get();
    }
    //________________________________________________________________________________________________________________

    /**
     * Método que permite responder a una PQRS, recibe los datos de la respuesta, llama a un servicio para enviar la respuesta, y envía una respuesta indicando que el mensaje fue enviado con éxito.
     */
    @Override
    public int responderPQRS(RegistroRespuestaDTO registroRespuestaDTO) throws Exception {

        Optional<Pqrs> pqrsEncontrada = pqrsRepo.findById(registroRespuestaDTO.codigoPQRS());

        Optional<Cuenta> cuentaEncontrada = cuentaRepo.findById(registroRespuestaDTO.codigoCuenta());

        if(cuentaEncontrada.isEmpty()){
            throw new Exception("El codigo" + registroRespuestaDTO.codigoPQRS() + " no esta asociado a ningun PQRS");
        }

        if(pqrsEncontrada.isEmpty()){
            throw new Exception("No existe tal PQRS con ese codigo");
        }

        Mensaje mensajeNuevo = new Mensaje();
        mensajeNuevo.setFechaCreacion(LocalDate.now());
        mensajeNuevo.setMensaje(registroRespuestaDTO.mensaje());
        mensajeNuevo.setPqrs(pqrsEncontrada.get());
        mensajeNuevo.setCuenta(cuentaEncontrada.get());

        Mensaje mensaje1 = mensajeRepo.save(mensajeNuevo);

        return mensaje1.getCodigo();
    }
    //________________________________________________________________________________________________________________


}
