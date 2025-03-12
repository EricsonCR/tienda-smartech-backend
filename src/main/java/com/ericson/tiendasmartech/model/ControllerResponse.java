package com.ericson.tiendasmartech.model;

import org.springframework.http.HttpStatus;

public record ControllerResponse(
        String message,
        HttpStatus status,
        Object data
) {
}
