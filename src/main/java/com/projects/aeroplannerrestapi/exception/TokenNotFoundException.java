package com.projects.aeroplannerrestapi.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.TOKEN_NOT_FOUND;

public class TokenNotFoundException extends RuntimeException {

    private static final Log LOG = LogFactory.getLog(TokenNotFoundException.class);

    public TokenNotFoundException() {
        super(TOKEN_NOT_FOUND);
        LOG.error(TOKEN_NOT_FOUND);
    }
}
