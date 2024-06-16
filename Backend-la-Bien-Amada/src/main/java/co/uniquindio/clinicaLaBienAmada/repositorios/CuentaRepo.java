package co.uniquindio.clinicaLaBienAmada.repositorios;

import co.uniquindio.clinicaLaBienAmada.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;



import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepo extends JpaRepository<Cuenta, Integer> {

    Optional<Cuenta> findByCorreo(String correo);
    //________________________________________________________________________________________________________________
}
