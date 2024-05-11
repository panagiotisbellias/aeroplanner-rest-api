package com.projects.aeroplannerrestapi.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.RESOURCE_NOT_FOUND;

public class ResourceNotFoundException extends RuntimeException {

    private static final Log LOG = LogFactory.getLog(ResourceNotFoundException.class);

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format(RESOURCE_NOT_FOUND, resourceName, fieldName, fieldValue));
        LOG.error(String.format(RESOURCE_NOT_FOUND, resourceName, fieldName, fieldValue));
    }
}
