package com.ericson.tiendasmartech.util;

import com.ericson.tiendasmartech.dto.UsuarioDto;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.regex.Pattern;

@Component
public class UsuarioUtil {
    public String validar(UsuarioDto usuarioDto) {
        String patronLetras = "^(?=.*[a-zA-Z]{2})([A-Za-z]{2,}( [A-Za-z]{2,})*)$";
        String patronDocumento = "^\\d{8,12}$";
        String patronCelular = "^9\\d{8}$";
        if (!Pattern.compile(patronLetras).matcher(usuarioDto.nombres()).matches())
            return "Error en campo nombres, solo caracteres de la a-z (minuscula, mayuscula, espacio, cantidad minima 2)";

        if (!Pattern.compile(patronLetras).matcher(usuarioDto.apellidos()).matches())
            return "Error en campo apellidos, solo caracteres de la a-z (minuscula, mayuscula, espacio, cantidad minima 2)";

        if (!Pattern.compile(patronDocumento).matcher(usuarioDto.numero()).matches())
            return "Error en el campo numero documento, solo digitos (0-9, minimo 8 y maximo 12)";

        Calendar fechaValida = Calendar.getInstance();
        fechaValida.add(Calendar.YEAR, -18);
        Calendar fechaNacimientoCal = Calendar.getInstance();
        fechaNacimientoCal.setTime(usuarioDto.nacimiento());
        // !fechaNacimientoCal.equals(fechaActual)
        if (fechaNacimientoCal.after(fechaValida))
            return "Error en campo nacimiento, debe ser mayor a 18 años.";

        if (!Pattern.compile(patronCelular).matcher(usuarioDto.telefono()).matches())
            return "Error en el campo numero celular, solo digitos (0-9, cantidad 9 digitos y empezar por 9)";

        return "OK";
    }
}
