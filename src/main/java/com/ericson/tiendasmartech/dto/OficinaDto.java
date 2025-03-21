package com.ericson.tiendasmartech.dto;

import java.time.LocalTime;

public record OficinaDto(
        long id,
        String nombre,
        String celular,
        LocalTime hora_inicio,
        LocalTime hora_fin,
        UsuarioDto usuario,
        DireccionDto direccion
) {
}
