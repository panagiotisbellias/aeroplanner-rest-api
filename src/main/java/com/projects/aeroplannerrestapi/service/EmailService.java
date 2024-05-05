package com.projects.aeroplannerrestapi.service;

import org.thymeleaf.context.Context;

public interface EmailService {

    void emailUser(String to, String subject, String text);

    void sendEmail(String to, String subject, String templateName, Context context);
}
