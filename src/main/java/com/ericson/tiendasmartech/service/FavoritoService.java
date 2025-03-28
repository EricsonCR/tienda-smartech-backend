package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.FavoritoDto;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface FavoritoService {
    ServiceResponse findAll();

    ServiceResponse findAllByUsuario(long id);

    ServiceResponse findById(long id);

    ServiceResponse create(FavoritoDto favoritoDto);

    ServiceResponse update(FavoritoDto favoritoDto);

    ServiceResponse delete(long id);
}
