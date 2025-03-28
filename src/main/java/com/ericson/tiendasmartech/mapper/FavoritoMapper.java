package com.ericson.tiendasmartech.mapper;

import com.ericson.tiendasmartech.dto.FavoritoDto;
import com.ericson.tiendasmartech.dto.MarcaDto;
import com.ericson.tiendasmartech.dto.ProductoDto;
import com.ericson.tiendasmartech.dto.UsuarioDto;
import com.ericson.tiendasmartech.entity.Favorito;
import com.ericson.tiendasmartech.entity.Marca;
import com.ericson.tiendasmartech.entity.Producto;
import com.ericson.tiendasmartech.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class FavoritoMapper {

    public Favorito toEntity(FavoritoDto favorito) {
        return new Favorito(
                favorito.id(),
                toUsuarioEntity(favorito.usuario()),
                toProductoEntity(favorito.producto()),
                null
        );
    }

    public FavoritoDto toDto(Favorito favorito) {
        return new FavoritoDto(
                favorito.getId(),
                toUsuarioDto(favorito.getUsuario()),
                toProductoDto(favorito.getProducto())
        );
    }

    private ProductoDto toProductoDto(Producto producto) {
        return new ProductoDto(
                producto.getId(),
                producto.getSku(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getSlogan(),
                toMarcaDto(producto.getMarca()),
                null,
                producto.getPrecio(),
                producto.getDescuento(),
                producto.getStock(),
                null,
                null
        );
    }

    private MarcaDto toMarcaDto(Marca marca) {
        return new MarcaDto(
                marca.getId(),
                marca.getNombre(),
                null
        );
    }

    private UsuarioDto toUsuarioDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private Usuario toUsuarioEntity(UsuarioDto usuario) {
        return new Usuario(
                usuario.id(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false,
                false,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private Producto toProductoEntity(ProductoDto producto) {
        return new Producto(
                producto.id(),
                producto.sku(),
                producto.nombre(),
                producto.descripcion(),
                producto.slogan(),
                new Marca(producto.marca().id(), producto.marca().nombre(), null, null),
                null,
                producto.precio(),
                producto.descuento(),
                producto.stock(),
                false,
                null,
                null,
                null,
                null
        );
    }
}
