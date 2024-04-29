package com.projects.aeroplannerrestapi.service;

public interface EmailService {

    void emailUser(String to, String subject, String text);
}
