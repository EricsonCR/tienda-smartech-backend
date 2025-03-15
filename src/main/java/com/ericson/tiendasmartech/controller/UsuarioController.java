package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.dto.UsuarioDto;
import com.ericson.tiendasmartech.entity.Usuario;
import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<ControllerResponse> findByEmail(@PathVariable String email) {
        ServiceResponse service = usuarioService.findByEmail(email);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @PutMapping("/update")
    public ResponseEntity<ControllerResponse> update(@RequestBody UsuarioDto usuarioDto) {
        ServiceResponse service = usuarioService.update(usuarioDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }
}
