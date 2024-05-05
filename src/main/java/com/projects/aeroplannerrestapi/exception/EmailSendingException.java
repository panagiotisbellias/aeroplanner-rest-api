package com.projects.aeroplannerrestapi.exception;

import jakarta.mail.MessagingException;

public class EmailSendingException extends MessagingException {

    public EmailSendingException(String s) {
        super(s);
    }
}
