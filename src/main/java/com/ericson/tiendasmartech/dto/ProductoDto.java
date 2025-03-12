package com.ericson.tiendasmartech.dto;

import java.util.List;

public record ProductoDto(
        long id,
        String sku,
        String nombre,
        String descripcion,
        String slogan,
        MarcaDto marca,
        CategoriaDto categoria,
        double precio,
        int descuento,
        int stock,
        List<FotoDto> fotos,
        List<EspecificacionDto> especificaciones) {
}
