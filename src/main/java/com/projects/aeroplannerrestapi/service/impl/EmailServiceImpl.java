package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.exception.EmailSendingException;
import com.projects.aeroplannerrestapi.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    @Value("${GMAIL_USERNAME}")
    private String gmailUsername;

    @Async
    @Override
    public void emailUser(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(gmailUsername);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Async
    @Override
    public void sendEmail(String to, String subject, String templateName, Context context) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            context.setVariable("header", "header");
            helper = new MimeMessageHelper(mimeMessage, true,"UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);
            helper.addInline("header", new ClassPathResource("static/images/header.jpg"), "image/jpeg");
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            try {
                throw new EmailSendingException(e.getMessage());
            } catch (EmailSendingException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
