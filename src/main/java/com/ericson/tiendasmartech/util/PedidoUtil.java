package com.ericson.tiendasmartech.util;

import com.ericson.tiendasmartech.dto.EmailDto;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PedidoUtil {
    public String generarNumeroPedido() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder numero = new StringBuilder();
        for (int i = 0; i < 12; i++)
            numero.append(secureRandom.nextInt(10));
        return numero.toString();
    }

    public EmailDto generarEmailPedido(String email, String numeroPedido) {
        String subject = "Pedido generado #" + numeroPedido;
        String message = "Hola estimado: " + email + "\n" + ". Hemos recibido su pedido y generado su comprobante de pago, gracias por su compra";
        return new EmailDto(email, subject, message, null);
    }

}
