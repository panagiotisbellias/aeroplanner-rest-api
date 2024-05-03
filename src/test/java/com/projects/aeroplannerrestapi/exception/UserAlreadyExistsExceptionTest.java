package com.projects.aeroplannerrestapi.exception;

import com.projects.aeroplannerrestapi.constants.ErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserAlreadyExistsExceptionTest {

    @Test
    void testUserAlreadyExistsException() {
        UserAlreadyExistsException userAlreadyExistsException = new UserAlreadyExistsException("email");
        Assertions.assertEquals(String.format(ErrorMessage.USER_ALREADY_EXISTS, "email"), userAlreadyExistsException.getMessage());
    }

}
