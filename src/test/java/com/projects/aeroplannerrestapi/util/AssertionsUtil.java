package com.projects.aeroplannerrestapi.util;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class AssertionsUtil {

    public static void assertNullBodyStatusCode(HttpStatusCode statusCode, ResponseEntity<?> response) {
        Assertions.assertNull(response.getBody());
        Assertions.assertEquals(statusCode, response.getStatusCode());
    }

}
