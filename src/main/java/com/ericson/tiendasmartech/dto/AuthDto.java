package com.ericson.tiendasmartech.dto;

import com.ericson.tiendasmartech.enums.Documento;

public record AuthDto(
        Documento documento,
        String numero,
        String nombres,
        String apellidos,
        String email,
        String password
) {
}
