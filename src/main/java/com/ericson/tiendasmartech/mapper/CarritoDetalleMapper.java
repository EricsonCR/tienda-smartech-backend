package com.ericson.tiendasmartech.mapper;

import com.ericson.tiendasmartech.dto.*;
import com.ericson.tiendasmartech.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarritoDetalleMapper {

    public CarritoDetalle toEntity(CarritoDetalleDto carritoDetalle){
        return new CarritoDetalle(
                carritoDetalle.id(),
                null,
                toProductoEntity(carritoDetalle.producto()),
                carritoDetalle.cantidad()
        );
    }

    public CarritoDetalleDto toDto(CarritoDetalle detalle){
        return new CarritoDetalleDto(
                detalle.getId(),
                toCarritoDto(detalle.getCarrito()),
                toProductoDto(detalle.getProducto()),
                detalle.getCantidad()
        );
    }

    public List<CarritoDetalleDto> toListDto(List<CarritoDetalle> detalles){
        List<CarritoDetalleDto> lista = new ArrayList<>();
        for (CarritoDetalle detalle : detalles) {
            lista.add(toDto(detalle));
        }
        return lista;
    }

    public List<CarritoDetalle> toListEntity(List<CarritoDetalleDto> detalles){
        List<CarritoDetalle> lista = new ArrayList<>();
        for (CarritoDetalleDto detalle : detalles) {
            lista.add(toEntity(detalle));
        }
        return lista;
    }

    private CarritoDto toCarritoDto(Carrito carrito) {
        return new CarritoDto(
                carrito.getId(),
                null,
                null
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
                toCategoriaDto(producto.getCategoria()),
                producto.getPrecio(),
                producto.getDescuento(),
                producto.getStock(),
                toListFotoDto(producto.getFotos()),
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

    private MarcaDto toMarcaDto(Marca marca) {
        return new MarcaDto(
                marca.getId(),
                marca.getNombre(),
                null
        );
    }

    private List<FotoDto> toListFotoDto(List<Foto> fotos) {
        List<FotoDto> lista = new ArrayList<>();
        for (Foto foto : fotos) {
            lista.add(toFotoDto(foto));
        }
        return lista;
    }

    private FotoDto toFotoDto(Foto foto) {
        return new FotoDto(
                foto.getId(),
                toGaleriaDto(foto.getGaleria())
        );
    }

    private GaleriaDto toGaleriaDto(Galeria galeria) {
        return new GaleriaDto(
                galeria.getId(),
                galeria.getUrl()
        );
    }

    private Producto toProductoEntity(ProductoDto producto) {
        List<Foto> fotos = toListFotoEntity(producto.fotos());
        return new Producto(
                producto.id(),
                producto.sku(),
                producto.nombre(),
                producto.descripcion(),
                producto.slogan(),
                toMarcaEntity(producto.marca()),
                toCategoriaEntity(producto.categoria()),
                producto.precio(),
                producto.descuento(),
                producto.stock(),
                false,
                null,
                null,
                fotos,
                null
        );
    }

    private List<Foto> toListFotoEntity(List<FotoDto> fotos) {
        List<Foto> lista = new ArrayList<>();
        for (FotoDto foto : fotos) {
            lista.add(toFotoEntity(foto));
        }
        return lista;
    }

    private Foto toFotoEntity(FotoDto foto) {
        return new Foto(
                foto.id(),
                toGaleriaEntity(foto.galeria()),
                null
        );
    }

    private Galeria toGaleriaEntity(GaleriaDto galeria) {
        return new Galeria(
                galeria.id(),
                galeria.url()
        );
    }

    private Marca toMarcaEntity(MarcaDto marca) {
        return new Marca(
                marca.id(),
                marca.nombre(),
                null,
                null
        );
    }

    private Categoria toCategoriaEntity(CategoriaDto categoria) {
        return new Categoria(
                categoria.id(),
                categoria.nombre(),
                null,
                null
        );
    }
}
