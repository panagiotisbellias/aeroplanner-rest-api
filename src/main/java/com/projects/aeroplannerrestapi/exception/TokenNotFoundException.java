package com.projects.aeroplannerrestapi.exception;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.TOKEN_NOT_FOUND;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException() {
        super(TOKEN_NOT_FOUND);
    }
}
