package com.projects.aeroplannerrestapi.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmailSendingExceptionTest {

    @Test
    void testEmailSendingException() {
        EmailSendingException emailSendingException = new EmailSendingException("message");
        Assertions.assertEquals("message", emailSendingException.getMessage());
    }

}
