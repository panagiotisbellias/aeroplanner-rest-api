package com.projects.aeroplannerrestapi.exception;

import com.projects.aeroplannerrestapi.constants.ErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.EMAIL;

@ExtendWith(MockitoExtension.class)
class UserAlreadyExistsExceptionTest {

    @Test
    void testUserAlreadyExistsException() {
        UserAlreadyExistsException userAlreadyExistsException = new UserAlreadyExistsException(EMAIL);
        Assertions.assertEquals(String.format(ErrorMessage.USER_ALREADY_EXISTS, EMAIL), userAlreadyExistsException.getMessage());
    }

}
