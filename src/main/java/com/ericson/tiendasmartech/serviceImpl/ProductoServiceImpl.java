package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.*;
import com.ericson.tiendasmartech.entity.*;
import com.ericson.tiendasmartech.mapper.ProductoMapper;
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
    private final ProductoMapper productoMapper;

    @Override
    public ServiceResponse listar() {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoDto> productoDtos = new ArrayList<>();
        if (!productos.isEmpty()) {
            for (Producto producto : productos) {
                productoDtos.add(productoMapper.entityToDto(producto));
            }
            return new ServiceResponse("Lista de productos exitosamente", HttpStatus.OK, productoDtos);
        }
        return new ServiceResponse("Lista vacia", HttpStatus.NOT_FOUND, null);
    }

    @Override
    public ServiceResponse buscarPorNombre(String nombre) {
        if (productoRepository.existsByNombre(nombre)) {
            Producto producto = productoRepository.findByNombre(nombre).orElse(new Producto());
            ProductoDto productoDto = productoMapper.entityToDto(producto);
            return new ServiceResponse("Producto encontrado", HttpStatus.OK, productoDto);
        }
        return new ServiceResponse("No existe el producto", HttpStatus.NOT_FOUND, null);
    }
}