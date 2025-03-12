package com.ericson.tiendasmartech.dto;

import java.util.List;

public record MarcaDto(
        long id,
        String nombre,
        List<ProductoDto> productos
) {
}
