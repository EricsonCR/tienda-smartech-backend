package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categoria")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping("/listar")
    public ResponseEntity<ControllerResponse> listar() {
        ServiceResponse service = categoriaService.listar();
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }
}
