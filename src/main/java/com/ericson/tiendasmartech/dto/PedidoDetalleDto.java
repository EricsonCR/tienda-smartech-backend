package com.ericson.tiendasmartech.dto;

public record PedidoDetalleDto(
        long id,
        PedidoDto pedido,
        ProductoDto producto,
        int cantidad,
        double precio
) {
}
