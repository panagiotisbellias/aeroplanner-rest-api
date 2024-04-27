package com.projects.aeroplannerrestapi.dto.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class ErrorDetailsResponseTest {

    @Mock
    LocalDateTime timestamp;

    @Test
    void testAllArgsConstructor() {
        ErrorDetailsResponse errorDetailsResponse = new ErrorDetailsResponse(timestamp, "message", "path");
        assertEquals(errorDetailsResponse);
    }

    @Test
    void testNoArgsConstructor() {
        ErrorDetailsResponse errorDetailsResponse = new ErrorDetailsResponse();
        Assertions.assertInstanceOf(ErrorDetailsResponse.class, errorDetailsResponse);
    }

    @Test
    void testSetters() {
        ErrorDetailsResponse errorDetailsResponse = new ErrorDetailsResponse();
        errorDetailsResponse.setTimestamp(timestamp);
        errorDetailsResponse.setMessage("message");
        errorDetailsResponse.setPath("path");
        assertEquals(errorDetailsResponse);
    }

    void assertEquals(ErrorDetailsResponse errorDetailsResponse) {
        Assertions.assertEquals(timestamp, errorDetailsResponse.getTimestamp());
        Assertions.assertEquals("message", errorDetailsResponse.getMessage());
        Assertions.assertEquals("path", errorDetailsResponse.getPath());
    }

}
