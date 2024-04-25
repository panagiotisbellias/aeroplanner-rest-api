package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.response.LoginResponse;
import com.projects.aeroplannerrestapi.dto.request.LoginRequest;
import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    UserResponse register(RegisterRequest registerRequest);

    LoginResponse authenticate(LoginRequest loginRequest);

    void logout(HttpServletRequest request);

    String extractTokenFromRequest(HttpServletRequest request);
}
