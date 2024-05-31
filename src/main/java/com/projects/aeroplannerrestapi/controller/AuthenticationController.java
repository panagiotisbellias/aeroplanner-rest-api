package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.response.LoginResponse;
import com.projects.aeroplannerrestapi.dto.request.LoginRequest;
import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.projects.aeroplannerrestapi.constants.OpenApiConstants.*;
import static com.projects.aeroplannerrestapi.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_AUTH)
public class AuthenticationController {

    private static final Log LOG = LogFactory.getLog(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @PostMapping(REGISTER)
    @Operation(summary = REGISTER_USER)
    @ApiResponses(@ApiResponse(responseCode = CREATED, description = USER_CREATED))
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        LOG.debug(String.format("== register(%s)", registerRequest.getEmail()));
        return new ResponseEntity<>(authenticationService.register(registerRequest), HttpStatus.CREATED);
    }

    @PostMapping(LOGIN)
    @Operation(summary = AUTHENTICATE_USER)
    @ApiResponses(@ApiResponse(responseCode = OK, description = USER_AUTHENTICATED))
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginRequest loginRequest) {
        LOG.debug(String.format("== authenticate(%s)", loginRequest.getEmail()));
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }

    @PostMapping(LOGOUT)
    @Operation(summary = LOGOUT_USER)
    @ApiResponses(@ApiResponse(responseCode = OK, description = LOGGED_OUT_SUCCESSFULLY))
    public ResponseEntity<String> logout (HttpServletRequest request) {
        LOG.debug(String.format("== logout(%s)", request.getContextPath()));
        authenticationService.logout(request);
        LOG.info("User " + LOGGED_OUT_SUCCESSFULLY);
        return ResponseEntity.ok(LOGGED_OUT_SUCCESSFULLY);
    }
}
