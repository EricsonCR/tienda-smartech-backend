package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.model.ServiceResponse;

public interface UsuarioService {
    ServiceResponse findByEmail(String email);
}
