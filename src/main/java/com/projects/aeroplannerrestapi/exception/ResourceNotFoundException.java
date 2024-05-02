package com.projects.aeroplannerrestapi.exception;

import static com.projects.aeroplannerrestapi.contstants.ErrorMessage.RESOURCE_NOT_FOUND;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format(RESOURCE_NOT_FOUND, resourceName, fieldName, fieldValue));
    }
}
