package com.projects.aeroplannerrestapi.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.EMAIL;
import static com.projects.aeroplannerrestapi.util.TestConstants.PASSWORD;

@ExtendWith(MockitoExtension.class)
class LoginRequestTest {

    @Test
    void testAlLArgsConstructor() {
        LoginRequest loginRequest = new LoginRequest(EMAIL, PASSWORD);
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
        loginRequest.setEmail(EMAIL);
        loginRequest.setPassword(PASSWORD);
        assertEquals(loginRequest);
    }

    void assertEquals(LoginRequest loginRequest) {
        Assertions.assertEquals(EMAIL, loginRequest.getEmail());
        Assertions.assertEquals(PASSWORD, loginRequest.getPassword());
    }

}
