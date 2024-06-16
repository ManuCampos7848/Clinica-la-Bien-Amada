package co.uniquindio.clinicaLaBienAmada.test;

import co.uniquindio.clinicaLaBienAmada.dto.ItemCitaDTO;
import co.uniquindio.clinicaLaBienAmada.dto.LoginDTO;
import co.uniquindio.clinicaLaBienAmada.dto.admin.RegistroMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.medico.DetalleAtencionMedicoDTO;
import co.uniquindio.clinicaLaBienAmada.dto.medico.DiaLibreDTO;
import co.uniquindio.clinicaLaBienAmada.dto.medico.RegistroAtencionDTO;
import co.uniquindio.clinicaLaBienAmada.model.Ciudad;
import co.uniquindio.clinicaLaBienAmada.model.Especialidad;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.AdmnistradorServicio;
import co.uniquindio.clinicaLaBienAmada.model.Medico;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.AutenticacionServicio;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.MedicoServicio;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ServicioMedicoTest {

    @Autowired
    private MedicoServicio medicoServicio;

    @Autowired
    private AutenticacionServicio autenticacionServicio;


    // ________________________________ Funcionales ______________________________________________________
    /*
    Permite al medico atender una cita
     */
    @Test
   // @Sql("classpath:dataset.sql")
    public void atencerCita() throws Exception {

        RegistroAtencionDTO atencion = new RegistroAtencionDTO(
                105,
                20,
                "Padece de estres post traumatico",
                "Parcetamol",
                "Semi grave"
        );

        int nuevo = medicoServicio.atenderCita(atencion);

        Assertions.assertNotEquals(0, nuevo);

    }

    @Test
    public void listarAtencionesPaciente() throws Exception {

        List<DetalleAtencionMedicoDTO> lista = medicoServicio.listarHistorialAtenciones(9);

        lista.forEach(System.out::println);
        //Si en el dataset creamos 2 pacientes, entonces el tamaño de la lista debe ser 2
        Assertions.assertEquals(1, lista.size());
    }

    @Test
    public void listarCitasRealizadas() throws Exception {

        List<ItemCitaDTO> lista = medicoServicio.listarCitasRealizadasMedico(24);

        lista.forEach(System.out::println);
        //Si en el dataset creamos 2 pacientes, entonces el tamaño de la lista debe ser 2
        Assertions.assertEquals(1, lista.size());
    }

    @Test
    public void verDetalleAtencion() throws Exception {

        DetalleAtencionMedicoDTO detalleAtencion = medicoServicio.verDetalleAtencion(104);

        System.out.println("\n" + "\n" + detalleAtencion.toString());
        Assertions.assertNotEquals(0, detalleAtencion);
    }


    @Test
    public void listarCitasPendientes() throws Exception {

        List<ItemCitaDTO> lista = medicoServicio.listarCitasPendientes(20);

        lista.forEach(System.out::println);
        //Si en el dataset creamos 2 pacientes, entonces el tamaño de la lista debe ser 2
        Assertions.assertEquals(2, lista.size());
    }

    @Test
    public void listarCitasCanceladas() throws Exception {

        List<ItemCitaDTO> lista = medicoServicio.listarCitasCanceladas(20);

        lista.forEach(System.out::println);
        //Si en el dataset creamos 2 pacientes, entonces el tamaño de la lista debe ser 2
        Assertions.assertEquals(1, lista.size());
    }

    @Test
    public void agendarDiaLibre() throws Exception {

        DiaLibreDTO dia = new DiaLibreDTO(
                20,
                LocalDate.of(2023, 12, 31));

        medicoServicio.agendarDiaLibre(dia);

        Assertions.assertNotEquals(0, dia);

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

    // ____________________________________________ No Funcionales _______________________________________





}
