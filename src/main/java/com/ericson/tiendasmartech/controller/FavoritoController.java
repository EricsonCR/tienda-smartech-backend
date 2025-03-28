package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.dto.FavoritoDto;
import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorito")
@RequiredArgsConstructor
public class FavoritoController {
    private final FavoritoService favoritoService;

    @PostMapping("/create")
    public ResponseEntity<ControllerResponse> create(@RequestBody FavoritoDto favoritoDto) {
        ServiceResponse service = favoritoService.create(favoritoDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ControllerResponse> delete(@PathVariable long id) {
        ServiceResponse service = favoritoService.delete(id);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }
}
