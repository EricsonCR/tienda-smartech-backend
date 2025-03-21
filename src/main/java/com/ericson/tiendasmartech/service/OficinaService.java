package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.OficinaDto;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface OficinaService {
    ServiceResponse listAll();

    ServiceResponse findById(String id);

    ServiceResponse create(OficinaDto oficinaDto);

    ServiceResponse update(OficinaDto oficinaDto);

    ServiceResponse delete(long id);
}
