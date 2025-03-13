package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.EmailDto;
import com.ericson.tiendasmartech.service.MailService;
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
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${email.username}")
    private String userFrom;

    @Override
    public Boolean sendEmail(EmailDto emailDto) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(userFrom);
            mailMessage.setTo(emailDto.email());
            mailMessage.setSubject(emailDto.asunto());
            mailMessage.setText(emailDto.mensaje());
            mailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean sendEmail(EmailDto emailDto, File file) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            String encode = StandardCharsets.UTF_8.name();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, encode);
            mimeMessageHelper.setFrom(userFrom);
            mimeMessageHelper.setTo(emailDto.email());
            mimeMessageHelper.setSubject(emailDto.asunto());
            mimeMessageHelper.setText(emailDto.mensaje(), true);
            mimeMessageHelper.addAttachment(file.getName(), file);
            mailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
