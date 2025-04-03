package com.ericson.tiendasmartech.util;

import com.ericson.tiendasmartech.dto.EmailDto;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class AuthUtil {
    public EmailDto generarEmailRegistro(String email, String token) {
        String token_url = URLEncoder.encode(token, StandardCharsets.UTF_8);
        String subject = "Email de validacion de registro";
        String message = "Hola " + email + ", bienvenido a nuestra tienda SmarTech.\n" + "Puedes validar tu registro haciendo click al siguiente enlace:\n" + "https://tienda-smartech.netlify.app/auth/validatedToken/" + token_url;
        return new EmailDto(email, subject, message, null);
    }
}
