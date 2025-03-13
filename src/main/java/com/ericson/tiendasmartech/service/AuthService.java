package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.AuthDto;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface AuthService {
    ServiceResponse signin(AuthDto authDto);

    ServiceResponse signup(AuthDto authDto);

    ServiceResponse validatedToken(String token);

    ServiceResponse sendTokenSignup(String email);

    ServiceResponse sendTokenForgot(String email);
}
