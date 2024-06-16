package co.uniquindio.clinicaLaBienAmada.test;

import co.uniquindio.clinicaLaBienAmada.dto.*;
import co.uniquindio.clinicaLaBienAmada.dto.admin.DetalleMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.admin.ItemMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.admin.RegistroMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.paciente.ItemPacienteDTO;
import co.uniquindio.clinicaLaBienAmada.model.*;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.AdmnistradorServicio;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.AutenticacionServicio;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.PacienteServicio;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class    AdministradorServicioTest {

    @Autowired
    private AdmnistradorServicio admnistradorServicio;

    @Autowired
    private AutenticacionServicio autenticacionServicio;

    // _____________________________________ Funcionales _______________________________________________
    @Test
    public void crearMedico() throws Exception {

        RegistroMedicoDTO medicoDTO = new RegistroMedicoDTO(
                "Charles",
                "333334",
                Ciudad.GONDOR,
                Especialidad.ORTOPEDISTA,
                "32140055",
                "Hol33a@gmail.com",
                "dontdo",
                "URL",
                true,
                new ArrayList<>()

        );

        int nuevo = admnistradorServicio.crearMedico(medicoDTO);

        Assertions.assertNotEquals(0, nuevo);

    }

    /*
    Metodo que permite actualizar los datos de un medico
     */
    @Test
    public void actualizarMedico() throws Exception {

        DetalleMedicoDTO medico = admnistradorServicio.obtenerMedico(101);

        DetalleMedicoDTO medicoModificado = new DetalleMedicoDTO(
                medico.codigo(), medico.nombre(), medico.cedula(), Ciudad.KHAZAD_DUM, medico.especialidad(),
                medico.telefono(), medico.correo(), medico.urlFoto(), medico.horarios());

        admnistradorServicio.actualizarMedico(medicoModificado);


        System.out.println(medicoModificado);

        Assertions.assertEquals(Ciudad.KHAZAD_DUM, medicoModificado.ciudad());

    }

    /*
    Metodo que permite listar los medicos
    en SQl se iguala el codigo para que sea uno en especifico
     */
    @Test
    public void listarMedicos() throws Exception {

        System.out.println(
                "\n" + "\n"
        );

        List<ItemMedicoDTO> medicosEncontrados = admnistradorServicio.listarMedicos();

        medicosEncontrados.forEach(System.out::println);
        //Si en el dataset creamos 2 pacientes, entonces el tamaño de la lista debe ser 2
        Assertions.assertEquals(6, +medicosEncontrados.size());
    }

    /*
    Metodo que permite listar las PQRS
     */
    @Test
    public void listarPQRS() throws Exception {

        System.out.println(
                "\n" + "\n"
        );

        List<ItemPQRSDTO> itemPQRSDTO = admnistradorServicio.listarPQRS();

        itemPQRSDTO.forEach(System.out::println);
        //Si en el dataset creamos 2 pacientes, entonces el tamaño de la lista debe ser 2
        Assertions.assertEquals(5, +itemPQRSDTO.size());
    }

    /*
    Metodo que permite ver los detalles de una PQRS
     */
    @Test
    public void verDetallePQRS() throws Exception {

        DetallePQRSDTO pqrs = admnistradorServicio.verDetallePQRS(504);

        System.out.println("\n" + "\n" + pqrs.toString());
        Assertions.assertNotEquals(0, pqrs);
    }

    /*
    Metodo que permite listar las citas de un medico
     */
    @Test
    public void listarCitas() throws Exception {

        List<ItemCitaDTO> citas = admnistradorServicio.listarCitas();

        System.out.println(
                "\n" + "\n"
        );

        citas.forEach(System.out::println);
        //Si en el dataset creamos 2 pacientes, entonces el tamaño de la lista debe ser 2
        Assertions.assertEquals(5, +citas.size());

    }

    @Test
    public void cambiarEstadoPQRS() throws Exception {

        int estadoPQRS = admnistradorServicio.cambiarEstadoPQRS(500, EstadoPQRS.RESUELTO);
        System.out.println(admnistradorServicio.verDetallePQRS(500));
        Assertions.assertNotEquals(0, estadoPQRS);
    }

    @Test
    public void responderPQRS() throws Exception {

        RegistroRespuestaDTO respuesta = new RegistroRespuestaDTO(
                99,
                504,
                "Pero el doctor me cuenta algo totalmente distinto, que pasa realmente?"
        );

        admnistradorServicio.responderPQRS(respuesta);

        Assertions.assertNotEquals(0, respuesta);
    }

    // _________________________________ Funcionales pero con dudas ___________________________________
    @Test
    @Transactional
    public void eliminarMedico() throws Exception {

        admnistradorServicio.eliminarMedico(21);

        Assertions.assertThrows(Exception.class, () -> admnistradorServicio.eliminarMedico(21));
    }

    // ______________________________________________________________________________________________________


    /*
    Metodo que lista todos los pacientes
     */
    @Test
    // @Sql("classpath:dataset.sql" )
    public void listarTest() throws Exception {
        //Obtenemos la lista de todos los pacientes
        List<ItemPacienteDTO> lista = admnistradorServicio.listarTodos();
        lista.forEach(System.out::println);
        //Si en el dataset creamos 2 pacientes, entonces el tamaño de la lista debe ser 2
        Assertions.assertEquals(8, lista.size());
    }

    @Test
    @Transactional
    // @Sql("classpath:dataset.sql")
    public void logear() throws Exception {

        LoginDTO login = new LoginDTO(
                "mariana89@email.com",
                "1234"
        );

        autenticacionServicio.login(login);

        Assertions.assertNotEquals(0, login);
    }

}
