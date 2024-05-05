package com.projects.aeroplannerrestapi.config;

import com.projects.aeroplannerrestapi.service.JwtService;
import com.projects.aeroplannerrestapi.service.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.*;
import static com.projects.aeroplannerrestapi.util.TestConstants.USER_EMAIL;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @InjectMocks
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    HandlerExceptionResolver handlerExceptionResolver;

    @Mock
    TokenBlacklistService tokenBlacklistService;

    @Mock
    UserDetailsService userDetailsService;

    @Mock
    JwtService jwtService;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @Mock
    UserDetails userDetails;

    @Test
    void testConstructor() {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(handlerExceptionResolver, tokenBlacklistService, userDetailsService, jwtService);
        Assertions.assertInstanceOf(JwtAuthenticationFilter.class, jwtAuthenticationFilter);
    }

    @Test
    void testDoFilterInternal() throws ServletException, IOException {
        Mockito.when(request.getHeader(AUTHORIZATION)).thenReturn(BEARER.concat(HEADER));
        Mockito.when(jwtService.extractUsername(HEADER)).thenReturn(USER_EMAIL);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verifyValidFlow();
        Mockito.verify(jwtService, Mockito.times(0)).isTokenValid(HEADER, userDetails);
        Mockito.verify(userDetails, Mockito.times(0)).getAuthorities();
        Mockito.verify(request, Mockito.times(0)).getRemoteAddr();
        Mockito.verify(request, Mockito.times(0)).getSession(false);
    }

    @Test
    void testDoFilterInternalInvalidJWT() throws ServletException, IOException {
        Mockito.when(request.getHeader(AUTHORIZATION)).thenReturn(BEARER.concat(HEADER));
        Mockito.when(jwtService.extractUsername(HEADER)).thenReturn(USER_EMAIL);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        Mockito.verify(jwtService, Mockito.times(0)).isTokenValid(HEADER, userDetails);
        verifyValidFlow();
        verifyNoMoreUserDetailsRequest();
    }

    void verifyValidFlow() {
        verifyRequestHeaderExtractedUsername();
        Mockito.verify(userDetails, Mockito.times(0)).getUsername();
    }

    @Test
    void testDoFilterInternalNullHeader() throws ServletException, IOException {
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        Mockito.verify(request).getHeader(AUTHORIZATION);
        Mockito.verifyNoInteractions(jwtService);
        verifyNoTokenBlacklistUserDetailServices();
        verifyNoUserDetailsNoMoreRequest();
    }

    @Test
    void testDoFilterInternalNoBearerHeader() throws ServletException, IOException {
        Mockito.when(request.getHeader(AUTHORIZATION)).thenReturn(HEADER);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        Mockito.verify(request).getHeader(AUTHORIZATION);
        Mockito.verifyNoInteractions(jwtService);
        verifyNoTokenBlacklistUserDetailServices();
        verifyNoUserDetailsNoMoreRequest();
    }

    @Test
    void testDoFilterInternalUserEmailNull() throws ServletException, IOException {
        Mockito.when(request.getHeader(AUTHORIZATION)).thenReturn(BEARER.concat(HEADER));
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verifyRequestHeaderExtractedUsername();
        verifyNoTokenBlacklistUserDetailServices();
        Mockito.verifyNoMoreInteractions(jwtService);
        Mockito.verify(userDetails, Mockito.times(0)).getUsername();
        verifyNoMoreUserDetailsRequest();
    }

    void verifyNoMoreUserDetailsRequest() {
        Mockito.verifyNoMoreInteractions(userDetails);
        Mockito.verifyNoMoreInteractions(request);
    }

    void verifyNoTokenBlacklistUserDetailServices() {
        Mockito.verifyNoInteractions(tokenBlacklistService);
        Mockito.verifyNoInteractions(userDetailsService);
    }

    @Test
    void testDoFilterInternalException() throws ServletException, IOException {
        Mockito.when(request.getHeader(AUTHORIZATION)).thenReturn(BEARER.concat(HEADER));
        Mockito.when(jwtService.extractUsername(HEADER)).thenReturn(USER_EMAIL);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verifyRequestHeaderExtractedUsername();
        verifyNoUserDetailsNoMoreRequest();
    }

    void verifyRequestHeaderExtractedUsername() {
        Mockito.verify(request).getHeader(AUTHORIZATION);
        Mockito.verify(jwtService).extractUsername(HEADER);
    }

    void verifyNoUserDetailsNoMoreRequest() {
        Mockito.verifyNoInteractions(userDetails);
        Mockito.verifyNoMoreInteractions(request);
    }

}
