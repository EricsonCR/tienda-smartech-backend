package com.ericson.tiendasmartech.dto;

import com.ericson.tiendasmartech.enums.Via;

public record DireccionDto(
        long id,
        Via via,
        String nombre,
        int numero,
        String referencia,
        String distrito,
        String provincia,
        String departamento,
        int codigo_postal
) {
}
