package com.ericson.tiendasmartech.dto;

import com.ericson.tiendasmartech.enums.Documento;
import com.ericson.tiendasmartech.enums.Via;

public record DireccionDto(
        long id,
        UsuarioDto usuario,
        Via via,
        Documento documento,
        String numero,
        String nombres,
        String celular,
        String direccion,
        String referencia,
        String distrito,
        String provincia,
        String departamento,
        int codigo_postal
) {
}
