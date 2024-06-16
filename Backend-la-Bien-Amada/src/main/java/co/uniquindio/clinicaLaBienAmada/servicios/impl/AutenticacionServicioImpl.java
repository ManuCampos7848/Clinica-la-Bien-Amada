package co.uniquindio.clinicaLaBienAmada.servicios.impl;

import co.uniquindio.clinicaLaBienAmada.dto.LoginDTO;
import co.uniquindio.clinicaLaBienAmada.dto.TokenDTO.TokenDTO;
import co.uniquindio.clinicaLaBienAmada.model.Cuenta;
import co.uniquindio.clinicaLaBienAmada.model.Medico;
import co.uniquindio.clinicaLaBienAmada.model.Paciente;
import co.uniquindio.clinicaLaBienAmada.repositorios.CuentaRepo;
import co.uniquindio.clinicaLaBienAmada.repositorios.MedicoRepo;
import co.uniquindio.clinicaLaBienAmada.repositorios.PacienteRepo;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.AutenticacionServicio;
import co.uniquindio.clinicaLaBienAmada.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutenticacionServicioImpl implements AutenticacionServicio {

    /**
     * Acceso a los repositorios apra realizar consultas y a metodos de la clase JWTUtils
     */
    private final CuentaRepo cuentaRepo;
    private  final PacienteRepo pacienteRepo;
    private final JWTUtils jwtUtils;
    //________________________________________________________________________________________________________________

    /**
     * Realiza la autenticación de un usuario a través de sus credenciales (correo y contraseña),
     * y devuelve un token JWT si la autenticación es exitosa.
     *
     * @param loginDTO Los datos de inicio de sesión que incluyen el correo electrónico y la contraseña.
     * @return Un objeto `TokenDTO` que contiene el token JWT generado después de la autenticación.
     * @throws Exception Si los datos de inicio de sesión son incorrectos, si la cuenta ha sido eliminada,
     *         si la contraseña ingresada es incorrecta, o si ocurre algún otro error durante el proceso de autenticación.
     */
   @Override
    public TokenDTO login(LoginDTO loginDTO) throws Exception {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<Cuenta> cuentaOptional = cuentaRepo.findByCorreo(loginDTO.correo());

       if (cuentaOptional.isEmpty()) {
           throw new Exception("Datos incorrectos, verifique nuevamente");
       }

       Cuenta cuenta = cuentaOptional.get();

       // Verificar el estado de la cuenta
       if (cuenta instanceof Paciente) {
           Paciente cuentaPaciente = pacienteRepo.findByCorreo(cuenta.getCorreo());
           if (!cuentaPaciente.isEstado()) {
               throw new Exception("La cuenta ha sido eliminada");
           }
       }

       // Aquí puedes agregar más lógica para otros tipos de cuenta, si es necesario

       // Resto del código para verificar la contraseña y generar el token
       if (!passwordEncoder.matches(loginDTO.password(), cuenta.getPassword())) {
           throw new Exception("La contraseña ingresada es incorrecta");
       }

       return new TokenDTO(crearToken(cuenta));


    }
    //________________________________________________________________________________________________________________


    /**
     * Crea un token JWT basado en el tipo de cuenta del usuario (paciente, médico o administrador).
     *
     * @param cuenta La cuenta del usuario para la cual se genera el token.
     * @return El token JWT generado que contiene información como el rol del usuario, su nombre y su identificador.
     */
    private String crearToken(Cuenta cuenta){
        String rol;
        String nombre;
        if( cuenta instanceof Paciente){
            rol = "paciente";
            nombre = ((Paciente) cuenta).getNombre();
        }else if( cuenta instanceof Medico){
            rol = "medico";
            nombre = ((Medico) cuenta).getNombre();
        }else{
            rol = "admin";
            nombre = "Administrador";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rol", rol);
        map.put("nombre", nombre);
        map.put("id", cuenta.getCodigo());

        return jwtUtils.generarToken(cuenta.getCorreo(), map);
    }
    //________________________________________________________________________________________________________________


}