package com.projects.aeroplannerrestapi.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoginRequestTest {

    @Test
    void testAlLArgsConstructor() {
        LoginRequest loginRequest = new LoginRequest("email","password");
        assertEquals(loginRequest);
    }

    @Test
    void testNoArgsConstructor() {
        LoginRequest loginRequest = new LoginRequest();
        Assertions.assertInstanceOf(LoginRequest.class, loginRequest);
    }

    @Test
    void testSetters() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email");
        loginRequest.setPassword("password");
        assertEquals(loginRequest);
    }

    void assertEquals(LoginRequest loginRequest) {
        Assertions.assertEquals("email", loginRequest.getEmail());
        Assertions.assertEquals("password", loginRequest.getPassword());
    }

}
