package com.projects.aeroplannerrestapi.dto.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoginResponseTest {

    @Test
    void testAllArgsConstructor() {
        LoginResponse loginResponse = new LoginResponse("token", 0L);
        assertEquals(loginResponse);
    }

    @Test
    void testNoArgsConstructor() {
        LoginResponse loginResponse = new LoginResponse();
        Assertions.assertInstanceOf(LoginResponse.class, loginResponse);
    }

    @Test
    void testSetters() {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("token");
        loginResponse.setExpiredIn(0L);
        assertEquals(loginResponse);
    }

    void assertEquals(LoginResponse loginResponse) {
        Assertions.assertEquals("token", loginResponse.getToken());
        Assertions.assertEquals(0L, loginResponse.getExpiredIn());
    }

}
