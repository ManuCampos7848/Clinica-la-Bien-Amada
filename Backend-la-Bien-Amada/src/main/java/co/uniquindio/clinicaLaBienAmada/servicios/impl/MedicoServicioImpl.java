package co.uniquindio.clinicaLaBienAmada.servicios.impl;

import co.uniquindio.clinicaLaBienAmada.dto.DetalleCitaDTO;
import co.uniquindio.clinicaLaBienAmada.dto.medico.DetalleAtencionMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.ItemCitaDTO;
import co.uniquindio.clinicaLaBienAmada.dto.medico.DiaLibreDTO;
import co.uniquindio.clinicaLaBienAmada.dto.medico.RegistroAtencionDTO;
import co.uniquindio.clinicaLaBienAmada.model.*;
import co.uniquindio.clinicaLaBienAmada.repositorios.*;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.MedicoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicoServicioImpl implements MedicoServicio {

    private final MedicoRepo medicoRepo;
    private final CitaRepo citaRepo;
    private final AtencionRepo atencionRepo;
    private final HorarioRepo horarioRepo;
    private final DiaLibreRepo diaLibreRepo;
    private final PacienteRepo pacienteRepo;
    //________________________________________________________________________________________________________________

    /**
     * Retorna el detalle completo de una cita especificada por su código.
     *
     * @param codigoCita El código único de la cita para la cual se desea obtener el detalle.
     * @return Un objeto DetalleCitaDTO que contiene el detalle completo de la cita.
     * @throws Exception Si no se encuentra ninguna cita con el código especificado.
     */
    @Override
    public DetalleCitaDTO verDetalleCita(int codigoCita) throws Exception {

        Optional<Cita> citaEncontrada = citaRepo.findById(codigoCita);

        if (citaEncontrada.isEmpty()) {
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
     * Lista todas las citas pendientes (estado PROGRAMADA) asociadas a un médico.
     *
     * @param codigoMedico El código del médico para el cual se desean listar las citas pendientes.
     * @return Una lista de objetos ItemCitaDTO que contienen las citas pendientes del médico.
     * @throws Exception Si no se encuentran citas pendientes para el médico especificado.
     */
    @Override
    public List<ItemCitaDTO> listarCitasPendientes(int codigoMedico) throws Exception {

        List<Cita> citasMedico = citaRepo.findAllByMedicoCodigo(codigoMedico);
        List<ItemCitaDTO> citasAMostrar = new ArrayList<>();

        for (Cita cita : citasMedico) {
            if (cita.getEstadoCita().equals(EstadoCita.PROGRAMADA)) {
                citasAMostrar.add(new ItemCitaDTO(
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

        return citasAMostrar;
    }
    //________________________________________________________________________________________________________________


    /**
     * Lista todas las citas canceladas (estado CANCELADA) asociadas a un médico.
     *
     * @param codigoMedico El código del médico para el cual se desean listar las citas canceladas.
     * @return Una lista de objetos ItemCitaDTO que contienen las citas canceladas del médico.
     * @throws Exception Si no se encuentran citas canceladas para el médico especificado.
     */
    @Override
    public List<ItemCitaDTO> listarCitasCanceladas(int codigoMedico) throws Exception {

        List<Cita> citasMedico = citaRepo.findAllByMedicoCodigo(codigoMedico);
        List<ItemCitaDTO> citasAMostrar = new ArrayList<>();

        for (Cita cita : citasMedico) {
            if(cita.getEstadoCita().equals(EstadoCita.CANCELADA)) {
                citasAMostrar.add(new ItemCitaDTO(
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


        return citasAMostrar;
    }
    //________________________________________________________________________________________________________________

    /**
     * Registra la atención médica realizada durante una cita, marcando la cita como COMPLETADA.
     *
     * @param registroAtencionDTO Datos de la atención médica a registrar.
     * @return El código de la nueva atención médica registrada.
     * @throws Exception Si no se encuentra la cita asociada al código especificado.
     */
    @Override
    public int atenderCita(RegistroAtencionDTO registroAtencionDTO) throws Exception {

        Optional<Cita> citaCodigo = citaRepo.findById(registroAtencionDTO.codigoCita());


        if(citaCodigo.isEmpty()){
            throw new Exception("No existe la cita");
        }

        Atencion atencion = new Atencion();

        atencion.setCita( citaCodigo.get() ) ;
        atencion.getCita().setEstadoCita(EstadoCita.COMPLETADA);

        atencion.setNotasMedicas(registroAtencionDTO.notasMedicas());
        atencion.setTratamiento(registroAtencionDTO.tratamiento());
        atencion.setDiagnostico(registroAtencionDTO.diagnostico());

        Atencion atencionNueva = atencionRepo.save(atencion);

        return atencionNueva.getCodigo();

    }
    //________________________________________________________________________________________________________________

    /**
     * Lista el historial de atenciones médicas asociadas a una cita específica.
     *
     * @param codigoCita El código único de la cita para la cual se desea obtener el historial de atenciones.
     * @return Una lista de objetos DetalleAtencionMedicoDTO que contienen el historial de atenciones médicas.
     * @throws Exception Si no se encuentran atenciones médicas asociadas a la cita especificada.
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
     * Lista todas las citas realizadas (estado COMPLETADA) asociadas a un médico.
     *
     * @param codigoMedico El código del médico para el cual se desean listar las citas realizadas.
     * @return Una lista de objetos ItemCitaDTO que contienen las citas realizadas del médico.
     * @throws Exception Si no se encuentran citas realizadas para el médico especificado.
     */
    @Override
    public List<ItemCitaDTO> listarCitasRealizadasMedico(int codigoMedico) throws Exception {

        List<Cita> citas = citaRepo.findAllByMedicoCodigo(codigoMedico);
        List<ItemCitaDTO> citasRealizadas = new ArrayList<>();


        for (Cita cita : citas) {
            if (cita.getEstadoCita().equals(EstadoCita.COMPLETADA)) {
                citasRealizadas.add(new ItemCitaDTO(
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

        return citasRealizadas;
    }
    //________________________________________________________________________________________________________________

    /**
     * Retorna el detalle de la atención médica asociada a una cita específica.
     *
     * @param codigoCita El código único de la cita para la cual se desea obtener el detalle de la atención médica.
     * @return Un objeto DetalleAtencionMedicoDTO que contiene el detalle de la atención médica.
     * @throws Exception Si no se encuentra ninguna atención médica asociada a la cita especificada.
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
     * Lista todos los días libres programados para un médico específico.
     *
     * @param codigoMedico El código del médico para el cual se desean listar los días libres.
     * @return Una lista de objetos DiaLibreDTO que contienen los días libres del médico.
     * @throws Exception Si no se encuentran días libres para el médico especificado.
     */
    @Override
    public List<DiaLibreDTO> listarDiaslibres(int codigoMedico) throws Exception {

        List<DiaLibre> diasLibresEncontrados = diaLibreRepo.findAllByMedicoCodigo(codigoMedico);

        List<DiaLibreDTO> respuesta = new ArrayList<>();

        for (DiaLibre d : diasLibresEncontrados){
            respuesta.add(new DiaLibreDTO(
                    d.getCodigo(),
                    d.getDia()
            ));
        }

        return respuesta;
    }
    //_______________________________________________________________________________________________


    /**
     * Agenda un nuevo día libre para un médico especificado.
     *
     * @param diaLibreDTO Datos del día libre a agendar.
     * @return El código del médico para el cual se agendó el día libre.
     * @throws Exception Si el médico no existe, el día ya está agendado como libre, ya tiene una cita programada para ese día, o ya tiene un día libre agendado en una fecha futura.
     */
    @Override
    public int agendarDiaLibre(DiaLibreDTO diaLibreDTO) throws Exception {

        Optional<Medico> optionalMedico = medicoRepo.findById(diaLibreDTO.codigoMedico());

        if(optionalMedico.isEmpty()){
            throw new Exception("No existe el id del médico");
        }

        List<DiaLibre> diasLibres = diaLibreRepo.findAllByMedicoCodigo(diaLibreDTO.codigoMedico());

        List<Cita> citasMedico = citaRepo.findAllByMedicoCodigo(diaLibreDTO.codigoMedico());

        for (Cita cita : citasMedico) {
            if (cita.getFechaCita().equals(diaLibreDTO.dia())) {
                throw new Exception("El dia que intenta agender como libre, ya tiene una cita antes agendada");

            }
        }

        for (DiaLibre diaLibre : diasLibres) {
            if (diaLibreDTO.dia().equals(diaLibre.getDia())) {
                throw new Exception("Este dia ya tiene un dia agendado");
            } else if( diaLibre.getDia().isAfter( LocalDate.now() ) ){
                throw new Exception("Usted ya tiene agendado un día libre para la fecha "+diaLibre.getDia());
            }

        }

        DiaLibre diaLibreMedico = new DiaLibre();
        diaLibreMedico.setDia(diaLibreDTO.dia());
        diaLibreMedico.setMedico(optionalMedico.get());

        diaLibreRepo.save(diaLibreMedico);


        return diaLibreDTO.codigoMedico();
    }
    //________________________________________________________________________________________________________________

}
