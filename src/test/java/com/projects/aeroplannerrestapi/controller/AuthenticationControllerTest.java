package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.LoginRequest;
import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
import com.projects.aeroplannerrestapi.dto.response.LoginResponse;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.service.AuthenticationService;
import com.projects.aeroplannerrestapi.util.AssertionsUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.projects.aeroplannerrestapi.constants.OpenApiConstants.LOGGED_OUT_SUCCESSFULLY;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    AuthenticationController authenticationController;

    @Mock
    AuthenticationService authenticationService;

    @Test
    void testConstructor() {
        AuthenticationController authenticationController = new AuthenticationController(authenticationService);
        Assertions.assertInstanceOf(AuthenticationController.class, authenticationController);
    }

    @Test
    void testRegister() {
        RegisterRequest registerRequest = Mockito.mock(RegisterRequest.class);
        ResponseEntity<UserResponse> response = authenticationController.register(registerRequest);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.CREATED, response);
    }

    @Test
    void testAuthenticate() {
        LoginRequest loginRequest = Mockito.mock(LoginRequest.class);
        ResponseEntity<LoginResponse> response = authenticationController.authenticate(loginRequest);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.OK, response);
    }

    @Test
    void testLogout() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        ResponseEntity<String> response = authenticationController.logout(request);

        Assertions.assertEquals(LOGGED_OUT_SUCCESSFULLY, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
