package com.projects.aeroplannerrestapi.dto.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class UserResponseTest {

    @Mock
    LocalDateTime createdAt;

    @Mock
    LocalDateTime updatedAt;

    @Test
    void testAllArgsConstructor() {
        UserResponse userResponse = new UserResponse(0L, "full name", "email", createdAt, updatedAt);
        assertEquals(userResponse);
    }

    @Test
    void testNoArgsConstructor() {
        UserResponse userResponse = new UserResponse();
        Assertions.assertInstanceOf(UserResponse.class, userResponse);
    }

    @Test
    void testSetters() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(0L);
        userResponse.setFullName("full name");
        userResponse.setEmail("email");
        userResponse.setCreatedAt(createdAt);
        userResponse.setUpdatedAt(updatedAt);
        assertEquals(userResponse);
    }

    void assertEquals(UserResponse userResponse) {
        Assertions.assertEquals(0L, userResponse.getId());
        Assertions.assertEquals("full name", userResponse.getFullName());
        Assertions.assertEquals("email", userResponse.getEmail());
        Assertions.assertEquals(createdAt, userResponse.getCreatedAt());
        Assertions.assertEquals(updatedAt, userResponse.getUpdatedAt());
    }

}
