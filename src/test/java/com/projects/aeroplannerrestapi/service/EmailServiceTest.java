package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.response.TicketResponse;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.service.impl.EmailServiceImpl;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EmailServiceTest {

    @InjectMocks
    EmailServiceImpl emailService;

    @Mock
    JavaMailSender emailSender;

    @Mock
    UserRepository userRepository;

    @Mock
    TemplateEngine templateEngine;

    @Mock
    Context context;

    @Mock
    MimeMessage mimeMessage;

    @Test
    void testConstructor() {

        EmailService emailService = new EmailServiceImpl(userRepository, emailSender, templateEngine);
        Assertions.assertInstanceOf(EmailService.class, emailService);
    }

    @Test
    void testEmailUser() {
        emailService.emailUser("to", "subject", "text");
        Mockito.verify(emailSender).send(ArgumentMatchers.any(SimpleMailMessage.class));
    }

    @Test
    void testSendEmail() {
        Mockito.when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        Mockito.when(templateEngine.process("template name", context)).thenReturn("html content");
        emailService.sendEmail(ArgumentMatchers.any(TicketResponse.class));

        Mockito.verify(emailSender).createMimeMessage();
        Mockito.verify(context).setVariable("header", "header");
        Mockito.verify(templateEngine).process("template name", context);
        Mockito.verify(emailSender).send(mimeMessage);
    }

}
