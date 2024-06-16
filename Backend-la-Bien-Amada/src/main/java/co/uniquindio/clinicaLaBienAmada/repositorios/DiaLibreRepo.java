package co.uniquindio.clinicaLaBienAmada.repositorios;

import co.uniquindio.clinicaLaBienAmada.model.DiaLibre;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaLibreRepo extends JpaRepository<DiaLibre, Integer> {

    List<DiaLibre> findAllByMedicoCodigo(int codigoMedico);
    //________________________________________________________________________________________________________________

    Optional<DiaLibre> findByMedicoCodigo(int codigoMedico);
    //________________________________________________________________________________________________________________
}
