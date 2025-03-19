package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.EmailDto;

import java.io.File;

public interface EmailService {
    boolean sendEmail(EmailDto emailDto);

    boolean sendEmailFile(EmailDto emailDto, File file);
}
