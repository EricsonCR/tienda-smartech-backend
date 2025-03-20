package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.dto.DomicilioDto;
import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.DomicilioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/domicilio")
@RequiredArgsConstructor
public class DomicilioController {
    private final DomicilioService domicilioService;

    @GetMapping("/listAllByUser/{id}")
    public ResponseEntity<ControllerResponse> listAllByUser(@PathVariable long id) {
        ServiceResponse service = domicilioService.listAllByUser(id);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ControllerResponse> findById(@PathVariable long id) {
        ServiceResponse service = domicilioService.findById(id);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @PostMapping("/save")
    public ResponseEntity<ControllerResponse> save(@RequestBody DomicilioDto domicilioDto) {
        ServiceResponse service = domicilioService.save(domicilioDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @PutMapping("/update")
    public ResponseEntity<ControllerResponse> update(@RequestBody DomicilioDto domicilioDto) {
        ServiceResponse service = domicilioService.update(domicilioDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ControllerResponse> delete(@PathVariable long id) {
        ServiceResponse service = domicilioService.delete(id);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }
}
