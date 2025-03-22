package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.PedidoDto;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface PedidoService {
    ServiceResponse registrar(PedidoDto pedidoDto);

    ServiceResponse findById(long id);
}
