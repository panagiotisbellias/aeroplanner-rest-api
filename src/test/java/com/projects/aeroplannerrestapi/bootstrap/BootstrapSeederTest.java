package com.projects.aeroplannerrestapi.bootstrap;

import com.projects.aeroplannerrestapi.constants.ErrorMessage;
import com.projects.aeroplannerrestapi.entity.Role;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.repository.RoleRepository;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.SUPER_ADMIN;

@ExtendWith(MockitoExtension.class)
class BootstrapSeederTest {

    @InjectMocks
    BootstrapSeeder bootstrapSeeder;

    @Mock
    RoleRepository roleRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    Role role;

    @Mock
    ContextRefreshedEvent event;

    @Test
    void testConstructor() {
        BootstrapSeeder bootstrapSeeder = new BootstrapSeeder(roleRepository, userRepository, passwordEncoder);
        Assertions.assertInstanceOf(BootstrapSeeder.class, bootstrapSeeder);
    }

    @Test
    void testOnApplicationEvent() {
        ContextRefreshedEvent event = Mockito.mock(ContextRefreshedEvent.class);
        Mockito.when(roleRepository.existsByName(RoleEnum.USER)).thenReturn(true);
        Mockito.when(roleRepository.findByName(RoleEnum.SUPER_ADMIN)).thenReturn(Optional.of(role));

        bootstrapSeeder.onApplicationEvent(event);
        Mockito.verify(roleRepository, Mockito.times(3)).existsByName(ArgumentMatchers.any(RoleEnum.class));
        Mockito.verify(roleRepository, Mockito.times(2)).save(ArgumentMatchers.any(Role.class));
        Mockito.verify(userRepository).existsByEmail(null);
        Mockito.verify(roleRepository).findByName(RoleEnum.SUPER_ADMIN);
        Mockito.verify(passwordEncoder).encode(null);
        Mockito.verify(userRepository).save(ArgumentMatchers.any(User.class));
    }

    @Test
    void testOnApplicationEventRoleNotFound() {
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> bootstrapSeeder.onApplicationEvent(event));
        Assertions.assertEquals(String.format(ErrorMessage.RESOURCE_NOT_FOUND, ErrorMessage.ROLE, ErrorMessage.NAME, SUPER_ADMIN), exception.getMessage());
    }

    @Test
    void testOnApplicationEventUserExists() {
        Mockito.when(userRepository.existsByEmail(null)).thenReturn(true);
        bootstrapSeeder.onApplicationEvent(event);

        Mockito.verify(roleRepository).existsByName(RoleEnum.USER);
        Mockito.verify(roleRepository).existsByName(RoleEnum.ADMIN);
        Mockito.verify(roleRepository).existsByName(RoleEnum.SUPER_ADMIN);
        Mockito.verify(roleRepository, Mockito.times(3)).save(ArgumentMatchers.any(Role.class));
        Mockito.verify(userRepository).existsByEmail(null);
        Mockito.verifyNoMoreInteractions(roleRepository);
        Mockito.verifyNoInteractions(passwordEncoder);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

}
