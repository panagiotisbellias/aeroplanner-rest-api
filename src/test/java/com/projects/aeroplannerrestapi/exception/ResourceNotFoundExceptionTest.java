package com.projects.aeroplannerrestapi.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResourceNotFoundExceptionTest {

    @Test
    void testResourceNotFoundException() {
        ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException("resource name", "field name", "field value");
        Assertions.assertEquals("resource name not found with field name : field value", resourceNotFoundException.getMessage());
    }

}
