package com.ericson.tiendasmartech.mapper;

import com.ericson.tiendasmartech.dto.*;
import com.ericson.tiendasmartech.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoriaMapper {
    public CategoriaDto toDto(Categoria categoria) {
        List<ProductoDto> lista = toListProductoDto(categoria.getProductos());
        return new CategoriaDto(
                categoria.getId(),
                categoria.getNombre(),
                lista
        );
    }

    private List<ProductoDto> toListProductoDto(List<Producto> productos) {
        List<ProductoDto> lista = new ArrayList<>();
        for (Producto producto : productos) {
            lista.add(toProductoDto(producto));
        }
        return lista;
    }

    private ProductoDto toProductoDto(Producto producto) {
        List<FotoDto> fotos = toListFotoDto(producto.getFotos());
        List<EspecificacionDto> especificaciones = toListEspecificacionDto(producto.getEspecificaciones());

        MarcaDto marca = toMarcaDto(producto.getMarca());
        CategoriaDto categoria = toCategoriaDto(producto.getCategoria());

        return new ProductoDto(
                producto.getId(),
                producto.getSku(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getSlogan(),
                marca,
                categoria,
                producto.getPrecio(),
                producto.getDescuento(),
                producto.getStock(),
                fotos,
                especificaciones
        );
    }

    private List<FotoDto> toListFotoDto(List<Foto> fotos) {
        List<FotoDto> lista = new ArrayList<>();
        for (Foto foto : fotos) {
            lista.add(toFotoDto(foto));
        }
        return lista;
    }

    private List<EspecificacionDto> toListEspecificacionDto(List<Especificacion> especificaciones) {
        List<EspecificacionDto> lista = new ArrayList<>();
        for (Especificacion especificacion : especificaciones) {
            lista.add(toEspecificacionDto(especificacion));
        }
        return lista;
    }

    private MarcaDto toMarcaDto(Marca marca) {
        return new MarcaDto(
                marca.getId(),
                marca.getNombre(),
                null
        );
    }

    private CategoriaDto toCategoriaDto(Categoria categoria) {
        return new CategoriaDto(
                categoria.getId(),
                categoria.getNombre(),
                null
        );
    }

    private EspecificacionDto toEspecificacionDto(Especificacion especificacion) {
        return new EspecificacionDto(
                especificacion.getId(),
                especificacion.getNombre(),
                especificacion.getDescripcion()
        );
    }

    private FotoDto toFotoDto(Foto foto) {
        return new FotoDto(
                foto.getId(),
                toGaleriaDto(foto.getGaleria())
        );
    }

    private GaleriaDto toGaleriaDto(Galeria galeria) {
        return new GaleriaDto(galeria.getId(), galeria.getUrl());
    }
}
