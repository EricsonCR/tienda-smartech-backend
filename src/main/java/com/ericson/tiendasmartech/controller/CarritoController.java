package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.dto.CarritoDetalleDto;
import com.ericson.tiendasmartech.dto.CarritoDto;
import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {
    private final CarritoService carritoService;

    @GetMapping("/buscarPorUsuario/{id}")
    public ResponseEntity<ControllerResponse> buscarPorUsuario(@PathVariable long id) {
        ServiceResponse service = carritoService.buscarPorUsuario(id);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @PostMapping("/registrar")
    public ResponseEntity<ControllerResponse> registrar(@RequestBody CarritoDto carritoDto) {
        ServiceResponse service = carritoService.registrar(carritoDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ControllerResponse> actualizar(@RequestBody CarritoDto carritoDto) {
        ServiceResponse service = carritoService.actualizar(carritoDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @PostMapping("/agregarItem/{id}")
    public ResponseEntity<ControllerResponse> agregaritem(@PathVariable long id,
                                                          @RequestBody CarritoDetalleDto carritoDetalleDto) {
        ServiceResponse service = carritoService.agregarItem(id, carritoDetalleDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @PostMapping("/eliminarItem/{id}")
    public ResponseEntity<ControllerResponse> eliminarItem(@PathVariable long id,
                                                           @RequestBody CarritoDetalleDto carritoDetalleDto) {
        ServiceResponse service = carritoService.eliminarItem(id, carritoDetalleDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @PostMapping("/sumarItem/{id}")
    public ResponseEntity<ControllerResponse> sumarItem(@PathVariable long id,
                                                        @RequestBody CarritoDetalleDto carritoDetalleDto) {
        ServiceResponse service = carritoService.sumarItem(id, carritoDetalleDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @PostMapping("/restarItem/{id}")
    public ResponseEntity<ControllerResponse> restarItem(@PathVariable long id,
                                                         @RequestBody CarritoDetalleDto carritoDetalleDto) {
        ServiceResponse service = carritoService.restarItem(id, carritoDetalleDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }
}