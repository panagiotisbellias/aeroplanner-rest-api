package com.projects.aeroplannerrestapi.config;

import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ApplicationConfigurationTest {

    @InjectMocks
    ApplicationConfiguration applicationConfiguration;

    @Mock
    UserRepository userRepository;

    @Test
    void testConstructor() {
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(userRepository);
        Assertions.assertInstanceOf(ApplicationConfiguration.class, applicationConfiguration);
    }

    @Test
    void testUserDetailsService() {
        User user = Mockito.mock(User.class);
        Mockito.when(userRepository.findByEmail("username")).thenReturn(Optional.of(user));
        Assertions.assertEquals(user, applicationConfiguration.userDetailsService().loadUserByUsername("username"));
    }

    @Test
    void testPasswordEncoder() {
        Assertions.assertInstanceOf(BCryptPasswordEncoder.class, applicationConfiguration.passwordEncoder());
    }

    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationConfiguration config = Mockito.mock(AuthenticationConfiguration.class);
        Assertions.assertNull(applicationConfiguration.authenticationManager(config));
    }

    @Test
    void testAuthenticationProvider() {
        Assertions.assertInstanceOf(AuthenticationProvider.class, applicationConfiguration.authenticationProvider());
    }

    @Test
    void testTemplateSimpleMessage() {
        Assertions.assertEquals("SimpleMailMessage: from=null; replyTo=null; to=; cc=; bcc=; sentDate=null; subject=Confirmation of Successful Payment for Your Ticket; text= Dear %s,\n" +
                "                 \n" +
                " We are pleased to inform you that we have received your payment for your flight ticket. Here are the details of your booking:\n" +
                "                 \n" +
                " - Passenger ID: %s\n" +
                " - Flight ID: %s\n" +
                " - Seat Number: %s\n" +
                " - Issue Date: %s\n" +
                " - Status: %s\n" +
                "                 \n" +
                " Thank you for choosing our airline. We look forward to serving you on your journey.\n" +
                " ", applicationConfiguration.templateSimpleMessage().toString());
    }

}
