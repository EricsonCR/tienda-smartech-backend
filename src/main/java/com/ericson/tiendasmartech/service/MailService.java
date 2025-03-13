package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.EmailDto;

import java.io.File;

public interface MailService {
    Boolean sendEmail(EmailDto emailDto);

    Boolean sendEmail(EmailDto emailDto, File file);
}
