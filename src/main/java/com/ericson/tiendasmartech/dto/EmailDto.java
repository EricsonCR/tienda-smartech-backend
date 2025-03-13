package com.ericson.tiendasmartech.dto;

import org.springframework.web.multipart.MultipartFile;

public record EmailDto(
        String email,
        String asunto,
        String mensaje,
        MultipartFile file
) {
}
