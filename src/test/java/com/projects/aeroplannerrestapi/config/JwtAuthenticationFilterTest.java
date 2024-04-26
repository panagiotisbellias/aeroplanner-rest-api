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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

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
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer header");
        Mockito.when(jwtService.extractUsername("header")).thenReturn("user email");
        Mockito.when(userDetailsService.loadUserByUsername("user email")).thenReturn(userDetails);
        Mockito.when(jwtService.isTokenValid("header", userDetails)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verifyValidFlow();
        Mockito.verify(userDetails).getAuthorities();
        Mockito.verify(request).getRemoteAddr();
        Mockito.verify(request).getSession(false);
    }

    @Test
    void testDoFilterInternalInvalidJWT() throws ServletException, IOException {
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer header");
        Mockito.when(jwtService.extractUsername("header")).thenReturn("user email");
        Mockito.when(userDetailsService.loadUserByUsername("user email")).thenReturn(userDetails);
        Mockito.when(jwtService.isTokenValid("header", userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verifyValidFlow();
        verifyNoMoreUserDetailsRequest();
    }

    void verifyValidFlow() {
        verifyValidFlowBeforeChecks();
        Mockito.verify(jwtService).isTokenValid("header", userDetails);
        Mockito.verify(userDetails, Mockito.times(0)).getUsername();
    }

    @Test
    void testDoFilterInternalNoBearerHeader() throws ServletException, IOException {
        Mockito.when(request.getHeader("Authorization")).thenReturn("header");
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        Mockito.verify(request).getHeader("Authorization");
        Mockito.verifyNoInteractions(jwtService);
        verifyNoTokenBlacklistUserDetailServices();
        verifyNoUserDetailsNoMoreRequest();
    }

    @Test
    void testDoFilterInternalUserEmailNull() throws ServletException, IOException {
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer header");
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
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer header");
        Mockito.when(jwtService.extractUsername("header")).thenReturn("user email");
        Mockito.when(userDetailsService.loadUserByUsername("user email")).thenThrow(UsernameNotFoundException.class);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verifyValidFlowBeforeChecks();
        Mockito.verifyNoMoreInteractions(jwtService);
        verifyNoUserDetailsNoMoreRequest();
    }

    void verifyValidFlowBeforeChecks() {
        verifyRequestHeaderExtractedUsername();
        Mockito.verify(tokenBlacklistService).isBlacklisted("header");
        Mockito.verify(userDetailsService).loadUserByUsername("user email");
    }

    void verifyRequestHeaderExtractedUsername() {
        Mockito.verify(request).getHeader("Authorization");
        Mockito.verify(jwtService).extractUsername("header");
    }

    void verifyNoUserDetailsNoMoreRequest() {
        Mockito.verifyNoInteractions(userDetails);
        Mockito.verifyNoMoreInteractions(request);
    }

}
