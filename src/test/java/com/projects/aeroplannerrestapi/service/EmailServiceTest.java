package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.response.TicketResponse;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.TicketStatusEnum;
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

import java.util.Optional;

import static com.projects.aeroplannerrestapi.constants.EmailConstants.*;
import static com.projects.aeroplannerrestapi.constants.ErrorMessage.EMAIL;

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
    MimeMessage mimeMessage;

    @Test
    void testConstructor() {

        EmailService emailService = new EmailServiceImpl(userRepository, emailSender, templateEngine);
        Assertions.assertInstanceOf(EmailService.class, emailService);
    }

    @Test
    void testEmailUser() {
        emailService.emailUser(TO, SUBJECT, "text");
        Mockito.verify(emailSender).send(ArgumentMatchers.any(SimpleMailMessage.class));
    }

    @Test
    void testSendEmail() {
        TicketResponse ticketResponse = Mockito.mock(TicketResponse.class);
        User passenger = Mockito.mock(User.class);

        Mockito.when(ticketResponse.getPassengerId()).thenReturn("0");
        Mockito.when(ticketResponse.getTicketStatusEnum()).thenReturn(TicketStatusEnum.BOARDED);
        Mockito.when(passenger.getEmail()).thenReturn(EMAIL);
        Mockito.when(userRepository.findById(0L)).thenReturn(Optional.of(passenger));
        Mockito.when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        Mockito.when(templateEngine.process(ArgumentMatchers.eq(EMAIL_TEMPLATE), ArgumentMatchers.any(Context.class))).thenReturn(EMAIL_TEMPLATE);
        emailService.sendEmail(ticketResponse);

        Mockito.verify(emailSender).createMimeMessage();
        Mockito.verify(ticketResponse, Mockito.times(2)).getPassengerId();
        Mockito.verify(userRepository).findById(0L);
        Mockito.verify(passenger).getFullName();
        Mockito.verify(ticketResponse).getFlightId();
        Mockito.verify(ticketResponse).getSeatNumber();
        Mockito.verify(ticketResponse).getIssueDate();
        Mockito.verify(ticketResponse).getTicketStatusEnum();
        Mockito.verify(passenger).getEmail();
        Mockito.verify(templateEngine).process(ArgumentMatchers.eq(EMAIL_TEMPLATE), ArgumentMatchers.any(Context.class));
        Mockito.verify(emailSender).send(mimeMessage);
    }

}
