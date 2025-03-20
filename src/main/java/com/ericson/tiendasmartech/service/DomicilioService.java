package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.DomicilioDto;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface DomicilioService {
    ServiceResponse listAllByUser(long id);

    ServiceResponse findById(long id);

    ServiceResponse save(DomicilioDto domicilioDto);

    ServiceResponse update(DomicilioDto domicilioDto);

    ServiceResponse delete(long id);
}
