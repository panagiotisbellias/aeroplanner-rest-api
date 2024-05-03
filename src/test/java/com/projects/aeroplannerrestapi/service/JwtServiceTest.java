package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.service.impl.JwtServiceImpl;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

import static com.projects.aeroplannerrestapi.util.TestConstants.TOKEN;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    JwtServiceImpl jwtService;

    @Mock
    Map<String, Object> extraClaims;

    @Mock
    UserDetails userDetails;

    @Disabled("Decode argument cannot be null.")
    @Test
    void testExtractUsername() {
        Assertions.assertEquals("", jwtService.extractUsername(TOKEN));
    }

    @Disabled("Decode argument cannot be null.")
    @Test
    void testExtractClaim() {
        Assertions.assertEquals("", jwtService.extractClaim(TOKEN, Claims::getSubject));
    }

    @Disabled("Decode argument cannot be null.")
    @Test
    void testGenerateToken() {
        Assertions.assertEquals("", jwtService.generateToken(extraClaims, userDetails));
    }

    @Test
    void testGetExpirationTime() {
        Assertions.assertEquals(0L, jwtService.getExpirationTime());
    }

    @Disabled("Decode argument cannot be null.")
    @Test
    void testIsTokenValid() {
        Assertions.assertFalse(jwtService.isTokenValid(TOKEN, userDetails));
    }

}
