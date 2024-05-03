package com.projects.aeroplannerrestapi.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.EMAIL;
import static com.projects.aeroplannerrestapi.util.TestConstants.FULL_NAME;
import static com.projects.aeroplannerrestapi.util.TestConstants.PASSWORD;

@ExtendWith(MockitoExtension.class)
class RegisterRequestTest {

    @Test
    void testAllArgsConstructor() {
        RegisterRequest registerRequest = new RegisterRequest(EMAIL, PASSWORD, FULL_NAME);
        assertEquals(registerRequest);
    }

    @Test
    void testNoArgsConstructor() {
        RegisterRequest registerRequest = new RegisterRequest();
        Assertions.assertInstanceOf(RegisterRequest.class, registerRequest);
    }

    @Test
    void testSetters() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(EMAIL);
        registerRequest.setPassword(PASSWORD);
        registerRequest.setFullName(FULL_NAME);
        assertEquals(registerRequest);
    }

    void assertEquals(RegisterRequest registerRequest) {
        Assertions.assertEquals(EMAIL, registerRequest.getEmail());
        Assertions.assertEquals(PASSWORD, registerRequest.getPassword());
        Assertions.assertEquals(FULL_NAME, registerRequest.getFullName());
    }

}
