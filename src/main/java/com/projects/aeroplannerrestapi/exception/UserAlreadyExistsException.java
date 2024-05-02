package com.projects.aeroplannerrestapi.exception;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.USER_ALREADY_EXISTS;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String email) {
        super(String.format(USER_ALREADY_EXISTS, email));
    }
}
