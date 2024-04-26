package com.projects.aeroplannerrestapi.bootstrap;

import com.projects.aeroplannerrestapi.bootsrap.AdminSeeder;
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

@ExtendWith(MockitoExtension.class)
class AdminSeederTest {

    @InjectMocks
    AdminSeeder adminSeeder;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    Role role;

    @Mock
    ContextRefreshedEvent event;

    @Test
    void testConstructor() {
        AdminSeeder adminSeeder = new AdminSeeder(userRepository, roleRepository, passwordEncoder);
        Assertions.assertInstanceOf(AdminSeeder.class, adminSeeder);
    }

    @Test
    void testOnApplicationEvent() {
        Mockito.when(roleRepository.findByName(RoleEnum.SUPER_ADMIN)).thenReturn(Optional.of(role));
        adminSeeder.onApplicationEvent(event);

        Mockito.verify(userRepository, Mockito.times(2)).existsByEmail(null);
        Mockito.verify(roleRepository).findByName(RoleEnum.SUPER_ADMIN);
        Mockito.verify(passwordEncoder).encode(null);
        Mockito.verify(userRepository).save(ArgumentMatchers.any(User.class));
    }

    @Test
    void testOnApplicationEventRoleNotFound() {
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> adminSeeder.onApplicationEvent(event));
        Assertions.assertEquals("Role not found with name : SUPER_ADMIN", exception.getMessage());
    }

    @Test
    void testOnApplicationEventUserExists() {
//        TODO Remove the second email check from actual method. It is repeated and cannot be covered from tests
        Mockito.when(userRepository.existsByEmail(null)).thenReturn(true);
        adminSeeder.onApplicationEvent(event);
        Mockito.verify(userRepository).existsByEmail(null);
        Mockito.verifyNoInteractions(roleRepository);
        Mockito.verifyNoInteractions(passwordEncoder);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

}
