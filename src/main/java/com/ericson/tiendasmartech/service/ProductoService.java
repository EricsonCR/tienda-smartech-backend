package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.model.ServiceResponse;

public interface ProductoService {
    ServiceResponse listar();

    ServiceResponse buscarPorNombre(String nombre);
}
