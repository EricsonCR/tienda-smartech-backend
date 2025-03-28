package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.*;
import com.ericson.tiendasmartech.entity.*;
import com.ericson.tiendasmartech.mapper.CarritoDetalleMapper;
import com.ericson.tiendasmartech.mapper.CarritoMapper;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.CarritoRepository;
import com.ericson.tiendasmartech.repository.ProductoRepository;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.CarritoService;
import com.ericson.tiendasmartech.util.CarritoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoMapper carritoMapper;
    private final CarritoDetalleMapper carritoDetalleMapper;
    private final CarritoUtil carritoUtil;

    @Override
    public ServiceResponse registrar(CarritoDto carritoDto) {
        try {
            Carrito carrito = carritoMapper.toEntity(carritoDto);
            if (carrito.getUsuario().getId() == 0)
                return new ServiceResponse("El carrito no tiene usuario asignado", HttpStatus.BAD_REQUEST, null);
            for (CarritoDetalle carritoDetalle : carrito.getCarritoDetalles())
                carritoDetalle.setCarrito(carrito);
            carritoRepository.save(carrito);
            return new ServiceResponse("Carrito agregado exitosamente", HttpStatus.OK, carritoMapper.toDto(carrito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse agregarItem(long id, CarritoDetalleDto carritoDetalleDto) {
        try {
            Carrito carrito = carritoRepository.findById(id).orElse(null);
            if (carrito == null) return new ServiceResponse("Carrito no encontrado", HttpStatus.BAD_REQUEST, null);
            if (carritoUtil.existsItem(carritoDetalleDto.producto().id(), carrito.getCarritoDetalles()))
                return new ServiceResponse("Producto ya existe", HttpStatus.FOUND, null);
            CarritoDetalle detalle = carritoDetalleMapper.toEntity(carritoDetalleDto);
            carrito.getCarritoDetalles().add(detalle);
            int indexLast = carrito.getCarritoDetalles().size() - 1;
            carrito.getCarritoDetalles().get(indexLast).setCarrito(carrito);
            carritoRepository.save(carrito);
            return new ServiceResponse("Producto agregado al carrito", HttpStatus.OK, carritoMapper.toDto(carrito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse eliminarItem(long id, CarritoDetalleDto carritoDetalleDto) {
        try {
            Carrito carrito = carritoRepository.findById(id).orElse(null);
            if (carrito == null) return new ServiceResponse("Carrito no encontrado", HttpStatus.BAD_REQUEST, null);
            int index = carritoUtil.indexItem(carritoDetalleDto.producto().id(), carrito.getCarritoDetalles());
            if (index == -1) return new ServiceResponse("El producto no existe", HttpStatus.BAD_REQUEST, null);
            carrito.getCarritoDetalles().remove(index);
            carritoRepository.save(carrito);
            return new ServiceResponse("Producto eliminado", HttpStatus.OK, carritoMapper.toDto(carrito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse sumarItem(long id, CarritoDetalleDto carritoDetalleDto) {
        try {
            Carrito carrito = carritoRepository.findById(id).orElse(null);
            if (carrito == null) return new ServiceResponse("Carrito no encontrado", HttpStatus.BAD_REQUEST, null);
            int index = carritoUtil.indexItem(carritoDetalleDto.producto().id(), carrito.getCarritoDetalles());
            if (index == -1) return new ServiceResponse("El producto no existe", HttpStatus.BAD_REQUEST, null);
            int cantidad = carrito.getCarritoDetalles().get(index).getCantidad();
            carrito.getCarritoDetalles().get(index).setCantidad(++cantidad);
            carritoRepository.save(carrito);
            return new ServiceResponse("Producto aumentado", HttpStatus.OK, carritoMapper.toDto(carrito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse restarItem(long id, CarritoDetalleDto carritoDetalleDto) {
        try {
            Carrito carrito = carritoRepository.findById(id).orElse(null);
            if (carrito == null) return new ServiceResponse("Carrito no encontrado", HttpStatus.BAD_REQUEST, null);
            int index = carritoUtil.indexItem(carritoDetalleDto.producto().id(), carrito.getCarritoDetalles());
            if (index == -1) return new ServiceResponse("El producto no existe", HttpStatus.BAD_REQUEST, null);
            int cantidad = carrito.getCarritoDetalles().get(index).getCantidad();
            if (cantidad > 1) carrito.getCarritoDetalles().get(index).setCantidad(--cantidad);
            else return new ServiceResponse("La cantidad minima debe ser 2", HttpStatus.BAD_REQUEST, null);
            carritoRepository.save(carrito);
            return new ServiceResponse("Producto disminuido", HttpStatus.OK, carritoMapper.toDto(carrito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse buscarPorUsuario(long id) {
        try {
            Carrito carrito = carritoRepository.findByUsuario(id).orElse(new Carrito());
            if (carrito.getUsuario() == null)
                return new ServiceResponse("No exsite carrito para este usuario", HttpStatus.BAD_REQUEST, null);
            return new ServiceResponse("Carrito encontrado", HttpStatus.OK, carritoMapper.toDto(carrito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse actualizar(CarritoDto carritoDto) {
        try {
            Carrito carrito = carritoRepository.findByUsuario(carritoDto.usuario().id()).orElse(new Carrito());
            if (carrito.getId() == 0) return new ServiceResponse("No exite carrito", HttpStatus.BAD_REQUEST, null);
            List<CarritoDetalle> lista = carrito.getCarritoDetalles();
            for (CarritoDetalleDto carritoDetalleDto : carritoDto.carritoDetalles()) {
                if (!carritoUtil.existsItem(carritoDetalleDto.producto().id(), carrito.getCarritoDetalles()))
                    lista.add(carritoDetalleMapper.toEntity(carritoDetalleDto));
            }
            carrito.setCarritoDetalles(lista);
            carrito.getCarritoDetalles().forEach(detalle -> detalle.setCarrito(carrito));
            carritoRepository.save(carrito);
            return new ServiceResponse("Carrito acoplado exitosamente", HttpStatus.OK, carritoMapper.toDto(carrito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}