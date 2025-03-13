package com.ericson.tiendasmartech.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String getToken(UserDetails userDetails);

    String getUsernameToken(String token);

    Boolean validateToken(String token, UserDetails userDetails);

    String getToken(String email);

    Boolean expiredToken(String token);
}
