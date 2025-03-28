package com.ericson.tiendasmartech.dto;

public record FavoritoDto(
        long id,
        UsuarioDto usuario,
        ProductoDto producto
) {
}
