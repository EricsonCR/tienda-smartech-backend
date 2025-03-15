package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.dto.DireccionDto;
import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.DireccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/direccion")
@RequiredArgsConstructor
public class DireccionController {
    private final DireccionService direccionService;

    @GetMapping("/listarPorUsuario/{id}")
    public ResponseEntity<ControllerResponse> listarPorUsuario(@PathVariable long id) {
        ServiceResponse service = direccionService.listarPorUsuario(id);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @PostMapping("/registrar")
    public ResponseEntity<ControllerResponse> registrar(@RequestBody DireccionDto direccionDto) {
        ServiceResponse service = direccionService.registrar(direccionDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ControllerResponse> actualizar(@RequestBody DireccionDto direccionDto) {
        ServiceResponse service = direccionService.actualizar(direccionDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<ControllerResponse> buscarPorId(@PathVariable long id) {
        ServiceResponse service = direccionService.buscarPorId(id);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ControllerResponse> eliminar(@PathVariable long id) {
        ServiceResponse service = direccionService.eliminar(id);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }
}
