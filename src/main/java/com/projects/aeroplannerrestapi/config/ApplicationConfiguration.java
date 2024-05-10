package com.projects.aeroplannerrestapi.config;

import com.projects.aeroplannerrestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.USER_NOT_FOUND;

@EnableAsync
@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private static final Log LOG = LogFactory.getLog(ApplicationConfiguration.class);

    private final UserRepository userRepository;

    @Bean
    UserDetailsService userDetailsService() {
        LOG.debug("userDetailsService()");
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        LOG.debug("passwordEncoder()");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        LOG.debug(String.format("authenticationManager(%s)", config));
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        LOG.debug("authenticationProvider()");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
