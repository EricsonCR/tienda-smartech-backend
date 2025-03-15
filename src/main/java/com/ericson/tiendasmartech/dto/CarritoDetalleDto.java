package com.ericson.tiendasmartech.dto;

public record CarritoDetalleDto(
        long id,
        CarritoDto carrito,
        ProductoDto producto,
        int cantidad) {
}
