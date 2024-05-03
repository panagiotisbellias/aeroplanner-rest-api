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

import static com.projects.aeroplannerrestapi.util.TestConstants.TOKEN;

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
        tokenBlacklistService.addToBlacklist(TOKEN);
        Mockito.verify(blacklist).add(TOKEN);
    }

    @Test
    void testIsBlacklisted() {
        Assertions.assertFalse(tokenBlacklistService.isBlacklisted(TOKEN));
    }

}
