package com.projects.aeroplannerrestapi.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegisterRequestTest {

    @Test
    void testAllArgsConstructor() {
        RegisterRequest registerRequest = new RegisterRequest("email", "password", "full name");
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
        registerRequest.setEmail("email");
        registerRequest.setPassword("password");
        registerRequest.setFullName("full name");
        assertEquals(registerRequest);
    }

    void assertEquals(RegisterRequest registerRequest) {
        Assertions.assertEquals("email", registerRequest.getEmail());
        Assertions.assertEquals("password", registerRequest.getPassword());
        Assertions.assertEquals("full name", registerRequest.getFullName());
    }

}
