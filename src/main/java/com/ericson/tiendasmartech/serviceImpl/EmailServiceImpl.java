package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.EmailDto;
import com.ericson.tiendasmartech.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Value("${email.username}")
    private String userFrom;

    @Override
    public boolean sendEmail(EmailDto emailDto) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(userFrom);
            mailMessage.setTo(emailDto.email());
            mailMessage.setSubject(emailDto.asunto());
            mailMessage.setText(emailDto.mensaje());
            emailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean sendEmailFile(EmailDto emailDto, File file) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            String encode = StandardCharsets.UTF_8.name();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, encode);
            mimeMessageHelper.setFrom(userFrom);
            mimeMessageHelper.setTo(emailDto.email());
            mimeMessageHelper.setSubject(emailDto.asunto());
            mimeMessageHelper.setText(emailDto.mensaje(), true);
            mimeMessageHelper.addAttachment(file.getName(), file);
            emailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }
}
