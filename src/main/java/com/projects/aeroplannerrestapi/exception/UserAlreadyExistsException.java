package com.projects.aeroplannerrestapi.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.USER_ALREADY_EXISTS;

public class UserAlreadyExistsException extends RuntimeException {

    private static final Log LOG = LogFactory.getLog(UserAlreadyExistsException.class);

    public UserAlreadyExistsException(String email) {
        super(String.format(USER_ALREADY_EXISTS, email));
        LOG.error(String.format(USER_ALREADY_EXISTS, email));
    }
}
