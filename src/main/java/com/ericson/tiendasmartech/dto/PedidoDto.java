package com.ericson.tiendasmartech.dto;

import com.ericson.tiendasmartech.enums.Entrega;
import com.ericson.tiendasmartech.enums.EstadoPedido;
import com.ericson.tiendasmartech.enums.MetodoPago;

import java.util.Date;
import java.util.List;

public record PedidoDto(
        long id,
        String numero,
        EstadoPedido estado,
        UsuarioDto usuario,
        Entrega entrega,
        ConsignatarioDto consignatario,
        DireccionDto direccion,
        MetodoPago metodo_pago,
        double precio_envio,
        double precio_cupon,
        double total,
        double igv,
        String comentarios,
        Date fecha_entrega,
        List<PedidoDetalleDto> pedidoDetalles
) {
}
