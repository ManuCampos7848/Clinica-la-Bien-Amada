package co.uniquindio.clinicaLaBienAmada.repositorios;

import co.uniquindio.clinicaLaBienAmada.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepo extends JpaRepository<Cita, Integer> {

    List<Cita> findAllByPacienteCodigo(int codigoPaciente);
    //________________________________________________________________________________________________________________
    Optional<Cita> findByMedicoCodigoAndFechaCita(int codigoMedico, LocalDate fecha);
    //________________________________________________________________________________________________________________
    List<Cita> findAllByPacienteCodigoAndMedicoCodigo(int codigoPaciente, int codigoMedico);
    //________________________________________________________________________________________________________________

    List<Cita> findAllByMedicoCodigo(int codigoMedico);
    //________________________________________________________________________________________________________________
    List<Cita> findByPacienteCodigo(int codigoPaciente);
    //________________________________________________________________________________________________________________
    Cita findCitaByAtencionCodigo(int codigoAtencion);
    //________________________________________________________________________________________________________________
    List<Cita> findAllByFechaCitaAndPacienteCodigo(LocalDate fecha, int codigoPaciente);
    //________________________________________________________________________________________________________________

}




