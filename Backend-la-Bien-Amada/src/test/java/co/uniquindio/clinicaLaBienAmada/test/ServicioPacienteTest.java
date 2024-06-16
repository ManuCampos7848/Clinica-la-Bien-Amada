package co.uniquindio.clinicaLaBienAmada.test;

import co.uniquindio.clinicaLaBienAmada.dto.*;
import co.uniquindio.clinicaLaBienAmada.dto.paciente.*;
import co.uniquindio.clinicaLaBienAmada.model.*;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.AutenticacionServicio;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.PacienteServicio;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class ServicioPacienteTest {

    @Autowired
    private PacienteServicio pacienteServicio;

    @Autowired
    private AutenticacionServicio autenticacionServicio;


    // _________________________ Funcionales _________________________________________________

    /*
    Metodo que permite registrar un paciente y cargar los datos quemados
    del datacenter en la base de datos
     */
   @Test
   @Sql("classpath:dataset.sql")
    public void registrarTest() throws Exception {

        //Creamos un objeto con los datos del paciente
        RegistroPacienteDTO pacienteDTO = new RegistroPacienteDTO(
                "2478",
                "Darly Daniela",
                "313",
                "xd",
                Ciudad.ARMENIA,
                LocalDate.of(1990, 10, 7),
                "Rinitis",
                Eps.NUEVAEPS,
                TipoDeSangre.A_POSITIVO,
                "copiasegu7848@gmail.com",
                "asdasd"
        );

        //Guardamos el registro usando el método del servicio
        int nuevo = pacienteServicio.registrarse(pacienteDTO);
        //Comprobamos que sí haya quedado guardado. Si se guardó debe retornar el código (no 0).
        Assertions.assertNotEquals(0, nuevo);
    }

    /*
    Metodo que permite agendar una cita
     */
    @Test
    public void agendarCita() throws Exception {



        Random random = new Random();

        // Genera un número aleatorio entre 0 y 4
        int numeroAleatorio = random.nextInt(5);

        // Obtén la sede correspondiente al número aleatorio
        Sede sede = Sede.values()[numeroAleatorio];


        RegistroCitaDTO registroCita = new RegistroCitaDTO(
                LocalDate.of(2000, 10, 7),
                "2:00 PM",
                "Consulta General",
                EstadoCita.PROGRAMADA,
                sede,
                100,
                20
        );

        int nuevo = pacienteServicio.agendarCita(registroCita);



        Assertions.assertNotEquals(0, nuevo);
    }

    /*
    Metodo que permite crear una PQRS
     */
    @Test
    public void crearPQRS() throws Exception {

        RegistroPQRSDTO pqrs = new RegistroPQRSDTO(
                1,
                "Me trato muy mal",
                9,
                EstadoPQRS.NUEVO,
                LocalDate.of(1990, 10, 7),
                "Malo"

        );

        int nuevo = pacienteServicio.crearPQRS(pqrs);

        Assertions.assertNotEquals(0, nuevo);
    }

    /*
    Metodo que permite ver todos los datos a detalle de un paciente
    a excepcion de la contraseña
     */
    @Test
    public void verDetallePaciente() throws Exception {


        DetallePacienteDTO detallesDpaciente = pacienteServicio.verDetallePaciente(9);


        System.out.println("\n" + "\n" + detallesDpaciente.toString());
        Assertions.assertNotEquals(0, detallesDpaciente);


    }

    /*
    Metodo que permite ver el detalle de una PQRS
     */

    @Test
    public void verDetallePQRS() throws Exception {

        DetallePQRSDTO detallePQRSDTO = pacienteServicio.verDetallePQRS(500);

        System.out.println("\n" + "\n" + detallePQRSDTO.toString());
        Assertions.assertNotEquals(0, detallePQRSDTO);

    }

    /*
    Metodo que permite listas las citas que tiene un paciente
     */
    @Test
    public void listarCitasPaciente() throws Exception {

        List<ItemCitaDTO> listaCitas = pacienteServicio.listarCitasPaciente(9);

        listaCitas.forEach(System.out::println);

        Assertions.assertEquals(1,  + listaCitas.size());
    }

    /*
    Metodo que permite ver el detalle de una cita
     */
    @Test
    public void verDetalleCita() throws Exception {

        DetalleCitaDTO detalleCita = pacienteServicio.verDetalleCita(100);

        System.out.println("\n" + "\n" + detalleCita.toString());
        Assertions.assertNotEquals(0, detalleCita);

    }


    /*
    Metodo que permite respoonder una PQRS
     */
    @Test
    public void responderPQRS() throws Exception {

        RegistroRespuestaDTO respuesta = new RegistroRespuestaDTO(
                100,
                504,
                "Supremamente grosero el man simplemente....."
        );

        pacienteServicio.responderPQRS(respuesta);

        Assertions.assertNotEquals(0, respuesta);
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

    // _______________________________________________ Funciona pero Ojito ____________________________

  /*  @Test
    public void enviarLinkRecuperacionContrasenia() throws Exception {

        pacienteServicio.enviarLinkRecuperacion("seguridadcopia720@gmail.com");

    } */

    @Test
    public void cambiarContrasenia() throws Exception {
        NuevaPasswordDTO nuevaPasswordDTO = new NuevaPasswordDTO(
                9,
                "123"
        );
        System.out.println("nuevaPasswordDTO.nuevaPassword: " + nuevaPasswordDTO.nuevaPassword());
        pacienteServicio.cambiarPassword(nuevaPasswordDTO);

        Assertions.assertNotEquals(0, nuevaPasswordDTO);
    }


    @Test
   // @Transactional
    //@Sql("classpath:dataset.sql")
    public void actualizarTest() throws Exception{

        DetallePacienteDTO guardado = pacienteServicio.verDetallePaciente(9);

        DetallePacienteDTO modificado = new DetallePacienteDTO(guardado.codigo(),
                guardado.cedula(), guardado.nombre(), guardado.telefono(),
                "Foto", guardado.ciudad(), guardado.fechaNacimiento(),
                guardado.alergias(), guardado.eps(), guardado.tipoSangre(), guardado.correo());

        pacienteServicio.editarPerfil(modificado);


        DetallePacienteDTO objetoModificado = pacienteServicio.verDetallePaciente(9);

        System.out.println(objetoModificado);

        Assertions.assertEquals("Foto" , objetoModificado.urlFoto());
    }

    @Test
    public void eliminarTest() throws Exception {

        pacienteServicio.eliminarCuenta(9);

        Assertions.assertTrue(pacienteServicio.eliminarCuenta(9));

    }
    /*
    Metodo que permite listar las PQRS de un paciente
     */
    @Test
    public void listarPQRSPaciente() throws Exception {


        System.out.println(
                "\n" + "\n"
        );

        List<ItemPQRSDTO> lista = pacienteServicio.listarPQRSPciente(9);
        lista.forEach(System.out::println);
        //Si en el dataset creamos 2 pacientes, entonces el tamaño de la lista debe ser 2
        Assertions.assertEquals(1,  + lista.size());
    }

    //______________METODOS DE FILTRAR POR FECHA Y CODIGO________________________________
/*
    Metodo que permite filtrar las citas por medio del codigo de un medico
     */
    @Test
    public void filtrarCitasPorCodigoMedico() throws Exception {

        List<FiltroBusquedaDTO> citas = pacienteServicio.filtrarCitasPorMedico(7, 22);

        citas.forEach(System.out::println);
        //Si en el dataset creamos 2 pacientes, entonces el tamaño de la lista debe ser 2
        Assertions.assertEquals(1,  + citas.size());
    }

    /*
       Metodo que permite filtrar las citas por medio de una fecha
        */
    @Test
    public void filtrarCitasPorFecha() throws Exception {

        List<FiltroBusquedaDTO> citas = pacienteServicio.
                filtrarCitasPorFecha(7, LocalDate.of(2023, 10, 24));

        citas.forEach(System.out::println);
        //Si en el dataset creamos 2 pacientes, entonces el tamaño de la lista debe ser 2
        Assertions.assertEquals(1,  + citas.size());
    }

    @Test
    public void listarMensajes()throws Exception{
        List<RespuestaDTO> citas = pacienteServicio.listarMensajes(500, 4);

        citas.forEach(System.out::println);
        //Si en el dataset creamos 2 pacientes, entonces el tamaño de la lista debe ser 2
        Assertions.assertEquals(3,  + citas.size());
    }
}
