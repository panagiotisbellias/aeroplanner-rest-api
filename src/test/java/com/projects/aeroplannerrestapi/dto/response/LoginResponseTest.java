package com.projects.aeroplannerrestapi.dto.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.projects.aeroplannerrestapi.util.TestConstants.TOKEN;

@ExtendWith(MockitoExtension.class)
class LoginResponseTest {

    @Test
    void testAllArgsConstructor() {
        LoginResponse loginResponse = new LoginResponse(TOKEN, 0L);
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
        loginResponse.setToken(TOKEN);
        loginResponse.setExpiredIn(0L);
        assertEquals(loginResponse);
    }

    void assertEquals(LoginResponse loginResponse) {
        Assertions.assertEquals(TOKEN, loginResponse.getToken());
        Assertions.assertEquals(0L, loginResponse.getExpiredIn());
    }

}
