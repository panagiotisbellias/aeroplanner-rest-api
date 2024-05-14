package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.constants.ErrorMessage;
import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
import com.projects.aeroplannerrestapi.entity.Role;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.exception.UserAlreadyExistsException;
import com.projects.aeroplannerrestapi.repository.RoleRepository;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.service.impl.SuperAdminServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class SuperAdminServiceTest {

    @InjectMocks
    SuperAdminServiceImpl superAdminService;

    @Mock
    RoleRepository roleRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RegisterRequest registerRequest;

    @Mock
    Role role;

    @Mock
    User user;

    @Test
    void testConstructor() {
        SuperAdminService superAdminService = new SuperAdminServiceImpl(roleRepository, userRepository, passwordEncoder);
        Assertions.assertInstanceOf(SuperAdminService.class, superAdminService);
    }

    @Test
    void testCreateAdministrator() {
        Mockito.when(roleRepository.findByName(RoleEnum.ADMIN)).thenReturn(Optional.of(role));
        Mockito.when(registerRequest.getEmail()).thenReturn("test@email.com");
        Assertions.assertNull(superAdminService.createAdministrator(registerRequest));
    }

    @Test
    void testCreateAdministratorRoleNotFound() {
        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> superAdminService.createAdministrator(registerRequest));
        Assertions.assertEquals(ErrorMessage.ROLE.concat(" not found with ").concat(ErrorMessage.NAME).concat(" : ADMIN"), resourceNotFoundException.getMessage());
    }

    @Test
    void testCreateAdministratorAlreadyExists() {
        Mockito.when(registerRequest.getEmail()).thenReturn(ErrorMessage.EMAIL);
        Mockito.when(roleRepository.findByName(RoleEnum.ADMIN)).thenReturn(Optional.of(role));
        Mockito.when(userRepository.existsByEmail(ErrorMessage.EMAIL)).thenReturn(true);

        UserAlreadyExistsException userAlreadyExistsException = Assertions.assertThrows(UserAlreadyExistsException.class, () -> superAdminService.createAdministrator(registerRequest));
        Assertions.assertEquals(ErrorMessage.USER.concat(" already exists with email: ").concat(ErrorMessage.EMAIL), userAlreadyExistsException.getMessage());
    }

    @Test
    void testGetAdministrator() {
        Mockito.when(userRepository.findByIdAndRolesName(0L, RoleEnum.ADMIN)).thenReturn(Optional.of(user));
        Assertions.assertNotNull(superAdminService.getAdministrator(0L));
    }

    @Test
    void testUpdateAdministrator() {
        Mockito.when(userRepository.findByIdAndRolesName(1L, RoleEnum.ADMIN)).thenReturn(Optional.of(user));
        Assertions.assertNull(superAdminService.updateAdministrator(1L, registerRequest));
    }

    @Test
    void testDeleteAdministrator() {
        Mockito.when(userRepository.findByIdAndRolesName(2L, RoleEnum.ADMIN)).thenReturn(Optional.of(user));
        superAdminService.deleteAdministrator(2L);

        Mockito.verify(userRepository).findByIdAndRolesName(2L, RoleEnum.ADMIN);
        Mockito.verify(userRepository).delete(user);
    }

}
