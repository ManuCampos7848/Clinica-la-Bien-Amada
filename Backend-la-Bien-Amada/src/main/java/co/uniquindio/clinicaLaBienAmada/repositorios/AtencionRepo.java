package co.uniquindio.clinicaLaBienAmada.repositorios;

import co.uniquindio.clinicaLaBienAmada.model.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AtencionRepo extends JpaRepository<Atencion, Integer> {

    List<Atencion> findAllByCita_Codigo(int codigoPaciente);
    //________________________________________________________________________________________________________________
    List<Atencion> findAllByCita_FechaCitaAndCita_Paciente_Codigo(LocalDate fecha, int codigoPaciente);
    //________________________________________________________________________________________________________________
    List<Atencion> findAllByCita_Paciente_CodigoAndCita_Medico_Codigo(int codigoPaciente, int codigoMedico);
    //________________________________________________________________________________________________________________
    Optional<Atencion> findByCitaCodigo(int codigoCita);
    //________________________________________________________________________________________________________________

}
