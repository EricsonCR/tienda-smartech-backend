package com.ericson.tiendasmartech.model;

import org.springframework.http.HttpStatus;

public record ServiceResponse(
        String message,
        HttpStatus status,
        Object data
) {
}
