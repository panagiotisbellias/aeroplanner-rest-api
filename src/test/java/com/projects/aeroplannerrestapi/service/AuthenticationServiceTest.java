package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.request.LoginRequest;
import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
import com.projects.aeroplannerrestapi.dto.response.LoginResponse;
import com.projects.aeroplannerrestapi.entity.Role;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.exception.TokenNotFoundException;
import com.projects.aeroplannerrestapi.exception.UserAlreadyExistsException;
import com.projects.aeroplannerrestapi.repository.RoleRepository;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.service.impl.AuthenticationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    AuthenticationServiceImpl authenticationService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    TokenBlacklistService tokenBlacklistService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtService jwtService;

    @Mock
    Role role;

    @Mock
    RegisterRequest registerRequest;

    @Mock
    LoginRequest loginRequest;

    @Mock
    HttpServletRequest request;

    @Test
    void testConstructor() {
        AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository, roleRepository, authenticationManager, tokenBlacklistService, passwordEncoder, jwtService);
        Assertions.assertInstanceOf(AuthenticationServiceImpl.class, authenticationService);
    }

    @Test
    void testRegister() {
        Mockito.when(roleRepository.findByName(RoleEnum.USER)).thenReturn(Optional.of(role));
        Assertions.assertNull(authenticationService.register(registerRequest));
    }

    @Test
    void testRegisterRoleAbsent() {
        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> authenticationService.register(registerRequest));
        Assertions.assertEquals("Role not found with Name : USER", resourceNotFoundException.getMessage());
    }

    @Test
    void testRegisterUserExists() {
        Mockito.when(roleRepository.findByName(RoleEnum.USER)).thenReturn(Optional.of(role));
        Mockito.when(registerRequest.getEmail()).thenReturn("email");
        Mockito.when(userRepository.existsByEmail("email")).thenReturn(true);

        UserAlreadyExistsException userAlreadyExistsException = Assertions.assertThrows(UserAlreadyExistsException.class, () -> authenticationService.register(registerRequest));
        Assertions.assertEquals("User already exists with email: email", userAlreadyExistsException.getMessage());
    }

    @Test
    void testAuthenticate() {
        User user = Mockito.mock(User.class);
        Mockito.when(loginRequest.getEmail()).thenReturn("email");
        Mockito.when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));

        LoginResponse loginResponse = authenticationService.authenticate(loginRequest);
        Assertions.assertNull(loginResponse.getToken());
        Assertions.assertEquals(0, loginResponse.getExpiredIn());
    }

    @Test
    void testAuthenticateUserNotFound() {
        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> authenticationService.authenticate(loginRequest));
        Assertions.assertEquals("User not found with Email : null", resourceNotFoundException.getMessage());
    }

    @Test
    void testLogout() {
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer header");
        authenticationService.logout(request);

        Mockito.verify(request).getHeader("Authorization");
        Mockito.verify(tokenBlacklistService).addToBlacklist("header");
    }

    @Test
    void testExtractTokenFromRequest() {
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer header");
        Assertions.assertEquals("header", authenticationService.extractTokenFromRequest(request));
    }

    @Test
    void testExtractTokenFromRequestNoBearerHeader() {
        Mockito.when(request.getHeader("Authorization")).thenReturn("header");
        Assertions.assertThrows(TokenNotFoundException.class, () -> authenticationService.extractTokenFromRequest(request));
    }

    @Test
    void testExtractTokenFromRequestNoTextInHeader() {
        Mockito.when(request.getHeader("Authorization")).thenReturn("");
        Assertions.assertThrows(TokenNotFoundException.class, () -> authenticationService.extractTokenFromRequest(request));
    }

}
