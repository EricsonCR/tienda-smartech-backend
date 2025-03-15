package com.ericson.tiendasmartech.dto;

import java.util.List;

public record CarritoDto(
        long id,
        UsuarioDto usuario,
        List<CarritoDetalleDto> carritoDetalles) {
}

