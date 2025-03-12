package com.ericson.tiendasmartech.dto;

import java.util.List;

public record CategoriaDto(
        long id,
        String nombre,
        List<ProductoDto> productos
) {
}
