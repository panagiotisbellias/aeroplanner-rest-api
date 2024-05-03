package com.projects.aeroplannerrestapi.dto.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.EMAIL;
import static com.projects.aeroplannerrestapi.util.TestConstants.FULL_NAME;

@ExtendWith(MockitoExtension.class)
class UserResponseTest {

    @Mock
    LocalDateTime createdAt;

    @Mock
    LocalDateTime updatedAt;

    @Test
    void testAllArgsConstructor() {
        UserResponse userResponse = new UserResponse(0L, FULL_NAME, EMAIL, createdAt, updatedAt);
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
        userResponse.setFullName(FULL_NAME);
        userResponse.setEmail(EMAIL);
        userResponse.setCreatedAt(createdAt);
        userResponse.setUpdatedAt(updatedAt);
        assertEquals(userResponse);
    }

    void assertEquals(UserResponse userResponse) {
        Assertions.assertEquals(0L, userResponse.getId());
        Assertions.assertEquals(FULL_NAME, userResponse.getFullName());
        Assertions.assertEquals(EMAIL, userResponse.getEmail());
        Assertions.assertEquals(createdAt, userResponse.getCreatedAt());
        Assertions.assertEquals(updatedAt, userResponse.getUpdatedAt());
    }

}
