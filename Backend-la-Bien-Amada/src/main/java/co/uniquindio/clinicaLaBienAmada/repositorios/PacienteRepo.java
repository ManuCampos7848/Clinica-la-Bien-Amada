package co.uniquindio.clinicaLaBienAmada.repositorios;

import co.uniquindio.clinicaLaBienAmada.model.Atencion;
import co.uniquindio.clinicaLaBienAmada.model.Paciente;
import org.hibernate.boot.archive.scan.spi.PackageInfoArchiveEntryHandler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepo extends JpaRepository<Paciente, Integer> {
   Paciente findByCedula(String cedula);
   //________________________________________________________________________________________________________________
   Paciente findByCorreo(String correo);
   //________________________________________________________________________________________________________________


}
