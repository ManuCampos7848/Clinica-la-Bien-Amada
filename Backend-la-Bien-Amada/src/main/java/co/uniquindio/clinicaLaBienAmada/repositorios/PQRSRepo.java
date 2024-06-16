package co.uniquindio.clinicaLaBienAmada.repositorios;

import co.uniquindio.clinicaLaBienAmada.model.Paciente;
import co.uniquindio.clinicaLaBienAmada.model.Pqrs;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PQRSRepo extends JpaRepository<Pqrs, Integer> {

    List<Pqrs> findAllByCita_Paciente_Codigo(int codigoPaciente);
    //________________________________________________________________________________________________________________

}
