package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.dto.AuthDto;
import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<ControllerResponse> signin(@RequestBody AuthDto authDto) {
        ServiceResponse service = authService.signin(authDto);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + service.data().toString());
        return ResponseEntity.ok().headers(headers)
                .body(new ControllerResponse(service.message(), service.status(), service.data()));
    }

    @PostMapping("/signup")
    public ResponseEntity<ControllerResponse> signup(@RequestBody AuthDto authDto) {
        ServiceResponse service = authService.signup(authDto);
        return ResponseEntity.ok(new ControllerResponse(service.message(), service.status(), service.data()));
    }
}
