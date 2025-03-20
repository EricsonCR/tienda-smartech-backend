package com.ericson.tiendasmartech.dto;

import com.ericson.tiendasmartech.enums.Documento;

public record ConsignatarioDto(
        long id,
        Documento documento,
        String numero,
        String nombres,
        String celular,
        String email
) {
}
