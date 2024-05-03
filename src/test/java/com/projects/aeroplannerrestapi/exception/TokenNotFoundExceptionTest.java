package com.projects.aeroplannerrestapi.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.projects.aeroplannerrestapi.contstants.ErrorMessage.TOKEN_NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class TokenNotFoundExceptionTest {

    @Test
    void testTokenNotFoundException() {
        TokenNotFoundException tokenNotFoundException = new TokenNotFoundException();
        Assertions.assertEquals(TOKEN_NOT_FOUND, tokenNotFoundException.getMessage());
    }

}
