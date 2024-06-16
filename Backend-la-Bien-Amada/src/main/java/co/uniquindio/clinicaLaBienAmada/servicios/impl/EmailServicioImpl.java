package co.uniquindio.clinicaLaBienAmada.servicios.impl;

import co.uniquindio.clinicaLaBienAmada.dto.EmailDTO;
import co.uniquindio.clinicaLaBienAmada.servicios.interfaces.EmailServicio;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServicioImpl implements EmailServicio {

    private final JavaMailSender javaMailSender;
    //________________________________________________________________________________________________________________

    /**
     * Envía un correo electrónico utilizando JavaMailSender.
     *
     * @param emailDTO El objeto EmailDTO que contiene la información del correo electrónico a enviar.
     * @throws Exception Si ocurre algún error durante el envío del correo electrónico.
     */
   @Override
    public void enviarCorreo(EmailDTO emailDTO) throws Exception {

        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje);

        helper.setSubject(emailDTO.asunto());
        helper.setText(emailDTO.mensaje(), true);
        helper.setTo(emailDTO.destinatario());
        helper.setFrom("no_reply@dominio.com");

        javaMailSender.send(mensaje);

    }
    //________________________________________________________________________________________________________________
}
