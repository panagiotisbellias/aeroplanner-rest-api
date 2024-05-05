package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    EmailServiceImpl emailService;

    @Mock
    JavaMailSender emailSender;

    @Mock
    TemplateEngine templateEngine;


    @Test
    void testConstructor() {
        EmailService emailService = new EmailServiceImpl(emailSender, templateEngine);
        Assertions.assertInstanceOf(EmailService.class, emailService);
    }

    @Test
    void testEmailUser() {
        emailService.emailUser("to", "subject", "text");
        Mockito.verify(emailSender).send(ArgumentMatchers.any(SimpleMailMessage.class));
    }

}
