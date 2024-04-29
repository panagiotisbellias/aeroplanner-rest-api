package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.service.impl.TokenBlacklistServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
class TokenBlacklistServiceTest {

    @InjectMocks
    TokenBlacklistServiceImpl tokenBlacklistService;

    @Mock
    Set<String> blacklist;

    @Test
    void testConstructor() {
        TokenBlacklistService tokenBlacklistService = new TokenBlacklistServiceImpl();
        Assertions.assertInstanceOf(TokenBlacklistService.class, tokenBlacklistService);
    }

    @Test
    void testAddToBlacklist() {
        tokenBlacklistService.addToBlacklist("token");
        Mockito.verify(blacklist).add("token");
    }

    @Test
    void testIsBlacklisted() {
        Assertions.assertFalse(tokenBlacklistService.isBlacklisted("token"));
    }

}
