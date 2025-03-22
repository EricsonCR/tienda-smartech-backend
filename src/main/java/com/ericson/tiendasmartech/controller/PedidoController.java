package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.dto.PedidoDto;
import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedido")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @PostMapping("/registrar")
    public ResponseEntity<ControllerResponse> registrar(@RequestBody PedidoDto pedidoDto) {
        ServiceResponse service = pedidoService.registrar(pedidoDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ControllerResponse> findById(@PathVariable Long id) {
        ServiceResponse service = pedidoService.findById(id);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }
}
