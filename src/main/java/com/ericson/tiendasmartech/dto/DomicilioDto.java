package com.ericson.tiendasmartech.dto;

public record DomicilioDto(
        long id,
        ConsignatarioDto consignatario,
        DireccionDto direccion,
        UsuarioDto usuario
) {
}
