package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping("/listar")
    public ResponseEntity<ControllerResponse> listar() {
        ServiceResponse service = productoService.listar();
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @GetMapping("/buscarPorNombre/{nombre}")
    public ResponseEntity<ControllerResponse> buscarPorNombre(@PathVariable String nombre) {
        ServiceResponse service = productoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

}
