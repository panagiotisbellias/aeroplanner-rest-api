package com.projects.aeroplannerrestapi.exception;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException() {
        super("Token not found");
    }
}
