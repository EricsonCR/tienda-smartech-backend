package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.CarritoDetalleDto;
import com.ericson.tiendasmartech.dto.CarritoDto;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface CarritoService {
    ServiceResponse registrar(CarritoDto carritoDto);

    ServiceResponse agregarItem(long id, CarritoDetalleDto carritoDetalleDto);

    ServiceResponse eliminarItem(long id, CarritoDetalleDto carritoDetalleDto);

    ServiceResponse sumarItem(long id, CarritoDetalleDto carritoDetalleDto);

    ServiceResponse restarItem(long id, CarritoDetalleDto carritoDetalleDto);

    ServiceResponse buscarPorUsuario(long id);

    ServiceResponse actualizar(CarritoDto carritoDto);
}
