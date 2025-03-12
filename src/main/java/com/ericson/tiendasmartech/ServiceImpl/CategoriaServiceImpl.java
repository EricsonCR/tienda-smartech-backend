package com.ericson.tiendasmartech.ServiceImpl;

import com.ericson.tiendasmartech.dto.*;
import com.ericson.tiendasmartech.entity.*;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.CategoriaRepository;
import com.ericson.tiendasmartech.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Override
    public ServiceResponse listar() {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaDto> lista = new ArrayList<>();
        if (!categorias.isEmpty()) {
            for (Categoria categoria : categorias) {
                lista.add(entityToDto(categoria));
            }
            return new ServiceResponse("Lista de categorias", HttpStatus.OK, lista);
        }
        return new ServiceResponse("Lista vacia", HttpStatus.NOT_FOUND, null);
    }

    private CategoriaDto entityToDto(Categoria categoria) {
        List<Producto> productos = categoria.getProductos();
        List<ProductoDto> lista = new ArrayList<>();
        for (Producto producto : productos) {
            lista.add(entityToDto(producto));
        }
        return new CategoriaDto(categoria.getId(), categoria.getNombre(), lista);
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