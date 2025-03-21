package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.dto.OficinaDto;
import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.OficinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oficina")
@RequiredArgsConstructor
public class OficinaController {
    private final OficinaService oficinaService;

    @GetMapping("/listAll")
    public ResponseEntity<ControllerResponse> listaAll() {
        ServiceResponse service = oficinaService.listAll();
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @PostMapping("/create")
    public ResponseEntity<ControllerResponse> create(@RequestBody OficinaDto oficinaDto) {
        ServiceResponse service = oficinaService.create(oficinaDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @PutMapping("/update")
    public ResponseEntity<ControllerResponse> update(@RequestBody OficinaDto oficinaDto) {
        ServiceResponse service = oficinaService.update(oficinaDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }
}
