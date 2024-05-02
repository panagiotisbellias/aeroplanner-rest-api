package com.projects.aeroplannerrestapi.constants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PathConstantsTest {

    @Test
    void testPathConstants() {
        Assertions.assertEquals("/api/v1", PathConstants.API_V1);
        Assertions.assertEquals("/{id}", PathConstants.ID);
        Assertions.assertEquals("/api/v1/auth", PathConstants.API_V1_AUTH);
        Assertions.assertEquals("/register", PathConstants.REGISTER);
        Assertions.assertEquals("/login", PathConstants.LOGIN);
        Assertions.assertEquals("/logout", PathConstants.LOGOUT);
        Assertions.assertEquals("/api/v1/flights", PathConstants.API_V1_FLIGHTS);
        Assertions.assertEquals("/api/v1/passengers", PathConstants.API_V1_PASSENGERS);
        Assertions.assertEquals("/api/v1/payments", PathConstants.API_V1_PAYMENTS);
        Assertions.assertEquals("/payment", PathConstants.PAYMENT);
        Assertions.assertEquals("/api/v1/reservations", PathConstants.API_V1_RESERVATIONS);
        Assertions.assertEquals("/api/v1/super-admins", PathConstants.API_V1_SUPER_ADMINS);
        Assertions.assertEquals("/api/v1/tickets", PathConstants.API_V1_TICKETS);
        Assertions.assertEquals("/api/v1/users", PathConstants.API_V1_USERS);
        Assertions.assertEquals("/me", PathConstants.ME);
    }

}
