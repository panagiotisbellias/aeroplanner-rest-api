package com.projects.aeroplannerrestapi.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfigurationSource;

@ExtendWith(MockitoExtension.class)
class SecurityConfigurationTest {

    @InjectMocks
    SecurityConfiguration securityConfiguration;

    @Mock
    AuthenticationProvider authenticationProvider;

    @Mock
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void testConstructor() {
        SecurityConfiguration securityConfiguration = new SecurityConfiguration(jwtAuthenticationFilter, authenticationProvider);
        Assertions.assertInstanceOf(SecurityConfiguration.class, securityConfiguration);
    }

    @Test
    void testSecurityFilterChain() throws Exception {
        HttpSecurity http = Mockito.mock(HttpSecurity.class);
        Mockito.when(http.csrf(ArgumentMatchers.any(Customizer.class))).thenReturn(http);
        Mockito.when(http.authorizeRequests(ArgumentMatchers.any(Customizer.class))).thenReturn(http);
        Mockito.when(http.sessionManagement(ArgumentMatchers.any(Customizer.class))).thenReturn(http);
        Mockito.when(http.authenticationProvider(authenticationProvider)).thenReturn(http);
        Assertions.assertNull(securityConfiguration.securityFilterChain(http));
    }

    @Test
    void testCorsConfigurationSource() {
        Assertions.assertInstanceOf(CorsConfigurationSource.class, securityConfiguration.corsConfigurationSource());
    }

}
