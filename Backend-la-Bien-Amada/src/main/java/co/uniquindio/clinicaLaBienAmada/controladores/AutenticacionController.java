package co.uniquindio.clinicaLaBienAmada.controladores;


import co.uniquindio.clinicaLaBienAmada.dto.LoginDTO;
import co.uniquindio.clinicaLaBienAmada.dto.TokenDTO.MensajeDTO;
import co.uniquindio.clinicaLaBienAmada.dto.TokenDTO.TokenDTO;
import co.uniquindio.clinicaLaBienAmada.dto.paciente.RegistroPacienteDTO;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.AutenticacionServicio;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.PacienteServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacionController {

    private final AutenticacionServicio autenticacionServicio;
    private final PacienteServicio pacienteServicio;

    /*
        Método que permite realizar el login recibiendo y validando los datos de inicio de
        sesión del usuario, llama a un servicio para autenticar dichos datos,
        genera un token de autenticación si los datos son correctos,
        y finalmente envía una respuesta al cliente con el token y un mensaje
        indicando que el proceso se completó correctamente.
     */
    @PostMapping("/login")
    public ResponseEntity<MensajeDTO<TokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO)
            throws Exception {

        TokenDTO tokenDTO = autenticacionServicio.login(loginDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, tokenDTO));
    }
    //________________________________________________________________________________________________________________

    /*
        Método que permite registrar un paciente, valida los datos, llama a un servicio para registrar al paciente,
        y envía una respuesta indicando que el registro fue exitoso.
     */
    @PostMapping("/registrar-paciente")
    public ResponseEntity<MensajeDTO<String>> registrarse(@Valid @RequestBody RegistroPacienteDTO pacienteDTO) throws Exception{
        pacienteServicio.registrarse(pacienteDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Paciente registrado correctamente"));
    }
    //________________________________________________________________________________________________________________
}

