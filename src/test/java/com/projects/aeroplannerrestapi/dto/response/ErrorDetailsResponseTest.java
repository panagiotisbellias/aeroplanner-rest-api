package com.projects.aeroplannerrestapi.dto.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static com.projects.aeroplannerrestapi.util.TestConstants.MESSAGE;
import static com.projects.aeroplannerrestapi.util.TestConstants.PATH;

@ExtendWith(MockitoExtension.class)
class ErrorDetailsResponseTest {

    @Mock
    LocalDateTime timestamp;

    @Test
    void testAllArgsConstructor() {
        ErrorDetailsResponse errorDetailsResponse = new ErrorDetailsResponse(timestamp, MESSAGE, PATH);
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
        errorDetailsResponse.setMessage(MESSAGE);
        errorDetailsResponse.setPath(PATH);
        assertEquals(errorDetailsResponse);
    }

    void assertEquals(ErrorDetailsResponse errorDetailsResponse) {
        Assertions.assertEquals(timestamp, errorDetailsResponse.getTimestamp());
        Assertions.assertEquals(MESSAGE, errorDetailsResponse.getMessage());
        Assertions.assertEquals(PATH, errorDetailsResponse.getPath());
    }

}
