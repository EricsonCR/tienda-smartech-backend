package com.ericson.tiendasmartech.dto;

import com.ericson.tiendasmartech.enums.Documento;
import com.ericson.tiendasmartech.enums.Rol;

import java.util.Date;
import java.util.List;

public record UsuarioDto(
        long id,
        Documento documento,
        String numero,
        Rol rol,
        String nombres,
        String apellidos,
        String direccion,
        String telefono,
        String email,
        Date nacimiento,
        List<DireccionDto> direcciones,
        List<PedidoDto> pedidos
) {
}