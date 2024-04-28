package com.projects.aeroplannerrestapi.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserAlreadyExistsExceptionTest {

    @Test
    void testUserAlreadyExistsException() {
        UserAlreadyExistsException userAlreadyExistsException = new UserAlreadyExistsException("email");
        Assertions.assertEquals("User already exists with email: email", userAlreadyExistsException.getMessage());
    }

}
