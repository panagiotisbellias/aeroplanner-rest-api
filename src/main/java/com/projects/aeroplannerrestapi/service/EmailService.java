package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.response.TicketResponse;

public interface EmailService {

    void emailUser(String to, String subject, String text);

    void sendEmail(TicketResponse ticketResponse);
}
