package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.DireccionDto;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface DireccionService {

    ServiceResponse listarPorUsuario(long id);

    ServiceResponse buscarPorId(long id);

    ServiceResponse registrar(DireccionDto direccionDto);

    ServiceResponse actualizar(DireccionDto direccionDto);

    ServiceResponse eliminar(long id);
}
