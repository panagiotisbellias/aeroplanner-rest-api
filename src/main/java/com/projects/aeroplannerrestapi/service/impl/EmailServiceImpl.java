package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.response.TicketResponse;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.exception.EmailSendingException;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

import static com.projects.aeroplannerrestapi.constants.EmailConstants.*;
import static com.projects.aeroplannerrestapi.constants.ErrorMessage.*;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.HEADER;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final Log LOG = LogFactory.getLog(EmailServiceImpl.class);

    private final UserRepository userRepository;
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String gmailUsername;

    @Async
    @Override
    public void emailUser(String to, String subject, String text) {
        LOG.debug(String.format("emailUser(%s, %s, %s)", to, subject, text));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(gmailUsername);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        LOG.info("Email sent %n");
        LOG.debug(String.format("to %s", to));
    }

    @Async
    @Override
    public void sendEmail(TicketResponse ticketResponse) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            String passengerId = ticketResponse.getPassengerId();
            User passenger = userRepository.findById(Long.valueOf(passengerId))
                    .orElseThrow(() -> new ResourceNotFoundException(USER, ID, passengerId));
            String passengerName = passenger.getFullName();
            Context context = new Context();
            Map<String, Object> variables = new HashMap<>();
            variables.put(TO, passengerName);
            variables.put(PASSENGER_ID, ticketResponse.getPassengerId());
            variables.put(FLIGHT_ID, ticketResponse.getFlightId());
            variables.put(SEAT_NUMBER, ticketResponse.getSeatNumber());
            variables.put(ISSUE_DATE, ticketResponse.getIssueDate());
            variables.put(STATUS, ticketResponse.getTicketStatusEnum().toString());
            variables.put(HEADER, HEADER);
            context.setVariables(variables);
            String to = passenger.getEmail();
            helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(SUBJECT);
            String htmlContent = templateEngine.process(EMAIL_TEMPLATE, context);
            helper.setText(htmlContent, true);
            helper.addInline(HEADER, new ClassPathResource("static/images/header.jpg"), "image/jpeg");
            emailSender.send(mimeMessage);
            LOG.info(String.format("Email sent to %s : %s", to, mimeMessage));
        } catch (MessagingException e) {
            try {
                throw new EmailSendingException(e.getMessage());
            } catch (EmailSendingException ex) {
                LOG.error(ex.getMessage());
                throw new RuntimeException(ex);
            }
        }
    }
}
