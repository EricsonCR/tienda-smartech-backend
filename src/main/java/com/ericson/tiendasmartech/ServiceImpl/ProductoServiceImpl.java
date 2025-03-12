package com.ericson.tiendasmartech.ServiceImpl;

import com.ericson.tiendasmartech.dto.*;
import com.ericson.tiendasmartech.entity.*;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.ProductoRepository;
import com.ericson.tiendasmartech.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public ServiceResponse listar() {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoDto> productoDtos = new ArrayList<>();
        if (!productos.isEmpty()) {
            for (Producto producto : productos) {
                productoDtos.add(entityToDto(producto));
            }
            return new ServiceResponse("Lista de productos exitosamente", HttpStatus.OK, productoDtos);
        }
        return new ServiceResponse("Lista vacia", HttpStatus.NOT_FOUND, null);
    }

    @Override
    public ServiceResponse buscarPorNombre(String nombre) {
        if (productoRepository.existsByNombre(nombre)) {
            Producto producto = productoRepository.findByNombre(nombre).orElse(new Producto());
            ProductoDto productoDto = entityToDto(producto);
            return new ServiceResponse("Producto encontrado", HttpStatus.OK, productoDto);
        }
        return new ServiceResponse("No existe el producto", HttpStatus.NOT_FOUND, null);
    }

    private ProductoDto entityToDto(Producto producto) {
        List<FotoDto> fotos = new ArrayList<>();
        for (Foto foto : producto.getFotos()) {
            fotos.add(new FotoDto(foto.getId(), new GaleriaDto(foto.getGaleria().getId(), foto.getGaleria().getUrl())));
        }

        List<EspecificacionDto> especificaciones = new ArrayList<>();
        for (Especificacion especificacion : producto.getEspecificaciones()) {
            especificaciones.add(new EspecificacionDto(especificacion.getId(), especificacion.getNombre(),
                    especificacion.getDescripcion()));
        }

        MarcaDto marca = new MarcaDto(producto.getMarca().getId(), producto.getMarca().getNombre(), null);
        CategoriaDto categoria = new CategoriaDto(producto.getCategoria().getId(), producto.getCategoria().getNombre(),
                null);

        return new ProductoDto(producto.getId(), producto.getSku(), producto.getNombre(), producto.getDescripcion(),
                producto.getSlogan(), marca, categoria, producto.getPrecio(), producto.getDescuento(),
                producto.getStock(), fotos, especificaciones);
    }
}