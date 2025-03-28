package com.ericson.tiendasmartech.mapper;

import com.ericson.tiendasmartech.dto.*;
import com.ericson.tiendasmartech.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CarritoMapper {

    private final CarritoDetalleMapper carritoDetalleMapper;

    public Carrito toEntity(CarritoDto carritoDto) {
        Usuario usuario = toUsuarioEntity(carritoDto.usuario());
        List<CarritoDetalle> detalles = carritoDetalleMapper.toListEntity(carritoDto.carritoDetalles());
        return new Carrito(
                carritoDto.id(),
                usuario,
                detalles,
                null,
                null
        );
    }

    public CarritoDto toDto(Carrito carrito) {
        List<CarritoDetalleDto> detalles = carritoDetalleMapper.toListDto(carrito.getCarritoDetalles());
        return new CarritoDto(
                carrito.getId(),
                toUsuarioDto(carrito.getUsuario()),
                detalles
        );
    }

    private UsuarioDto toUsuarioDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                usuario.getDocumento(),
                usuario.getNumero(),
                usuario.getRol(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getDireccion(),
                usuario.getTelefono(),
                usuario.getEmail(),
                usuario.getNacimiento(),
                null,
                null,
                null
        );
    }

    private Usuario toUsuarioEntity(UsuarioDto usuario) {
        return new Usuario(
                usuario.id(),
                usuario.documento(),
                usuario.numero(),
                usuario.rol(),
                usuario.nombres(),
                usuario.apellidos(),
                usuario.direccion(),
                usuario.telefono(),
                usuario.email(),
                null,
                false,
                false,
                usuario.nacimiento(),
                null,
                null,
                null,
                null,
                null
        );
    }
}
