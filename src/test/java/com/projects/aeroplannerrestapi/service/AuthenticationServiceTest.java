package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.constants.ErrorMessage;
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

import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.*;

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
        Mockito.when(registerRequest.getFullName()).thenReturn("Test User");
        Assertions.assertNull(authenticationService.register(registerRequest));
    }

    @Test
    void testRegisterRoleAbsent() {
        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> authenticationService.register(registerRequest));
        Assertions.assertEquals(String.format(ErrorMessage.RESOURCE_NOT_FOUND, ErrorMessage.ROLE, ErrorMessage.NAME, "USER"), resourceNotFoundException.getMessage());
    }

    @Test
    void testRegisterUserExists() {
        Mockito.when(roleRepository.findByName(RoleEnum.USER)).thenReturn(Optional.of(role));
        Mockito.when(registerRequest.getEmail()).thenReturn(ErrorMessage.EMAIL);
        Mockito.when(userRepository.existsByEmail(ErrorMessage.EMAIL)).thenReturn(true);

        UserAlreadyExistsException userAlreadyExistsException = Assertions.assertThrows(UserAlreadyExistsException.class, () -> authenticationService.register(registerRequest));
        Assertions.assertEquals(String.format(ErrorMessage.USER_ALREADY_EXISTS, ErrorMessage.EMAIL), userAlreadyExistsException.getMessage());
    }

    @Test
    void testAuthenticate() {
        User user = Mockito.mock(User.class);
        Mockito.when(loginRequest.getEmail()).thenReturn(ErrorMessage.EMAIL);
        Mockito.when(userRepository.findByEmail(ErrorMessage.EMAIL)).thenReturn(Optional.of(user));

        LoginResponse loginResponse = authenticationService.authenticate(loginRequest);
        Assertions.assertNull(loginResponse.getToken());
        Assertions.assertEquals(0, loginResponse.getExpiredIn());
    }

    @Test
    void testAuthenticateUserNotFound() {
        Mockito.when(loginRequest.getEmail()).thenReturn("test@email.com");
        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> authenticationService.authenticate(loginRequest));
        Assertions.assertEquals(ErrorMessage.USER_NOT_FOUND.concat(" with ").concat(ErrorMessage.EMAIL).concat(" : test@email.com"), resourceNotFoundException.getMessage());
    }

    @Test
    void testLogout() {
        Mockito.when(request.getHeader(AUTHORIZATION)).thenReturn(BEARER.concat(HEADER));
        authenticationService.logout(request);

        Mockito.verify(request).getHeader(AUTHORIZATION);
        Mockito.verify(tokenBlacklistService).addToBlacklist(HEADER);
    }

    @Test
    void testExtractTokenFromRequest() {
        Mockito.when(request.getHeader(AUTHORIZATION)).thenReturn(BEARER.concat(HEADER));
        Assertions.assertEquals(HEADER, authenticationService.extractTokenFromRequest(request));
    }

    @Test
    void testExtractTokenFromRequestNoBearerHeader() {
        Mockito.when(request.getHeader(AUTHORIZATION)).thenReturn(HEADER);
        Assertions.assertThrows(TokenNotFoundException.class, () -> authenticationService.extractTokenFromRequest(request));
    }

    @Test
    void testExtractTokenFromRequestNoTextInHeader() {
        Mockito.when(request.getHeader(AUTHORIZATION)).thenReturn("");
        Assertions.assertThrows(TokenNotFoundException.class, () -> authenticationService.extractTokenFromRequest(request));
    }

}
