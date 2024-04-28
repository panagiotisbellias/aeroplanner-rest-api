package com.projects.aeroplannerrestapi.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TokenNotFoundExceptionTest {

    @Test
    void testTokenNotFoundException() {
        TokenNotFoundException tokenNotFoundException = new TokenNotFoundException();
        Assertions.assertEquals("Token not found", tokenNotFoundException.getMessage());
    }

}
