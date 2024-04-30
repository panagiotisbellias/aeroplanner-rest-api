package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedUserResponse;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Page<User> page;

    @Test
    void testConstructor() {
        UserService userService = new UserServiceImpl(userRepository);
        Assertions.assertInstanceOf(UserService.class, userService);
    }

    @Disabled("Authentication object should be mocked")
    @Test
    void testGetAuthenticateUser() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        userService.getAuthenticatedUser();
    }

    @Test
    void testGetAllUsersAsc() {
        Mockito.when(userRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);
        PaginatedAndSortedUserResponse response = userService.getAllUsers(1, 2, "sort by", "asc");

        Assertions.assertEquals(1, response.getContent().size());
        Assertions.assertEquals(0, response.getPageNumber());
        Assertions.assertEquals(0, response.getPageSize());
        Assertions.assertEquals(0L, response.getTotalElements());
        Assertions.assertEquals(0, response.getTotalPages());
        Assertions.assertFalse(response.isLast());
    }

    @Test
    void testGetAllUsersDesc() {
        Mockito.when(userRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);
        PaginatedAndSortedUserResponse response = userService.getAllUsers(1, 2, "sort by", "desc");

        Assertions.assertEquals(1, response.getContent().size());
        Assertions.assertEquals(0, response.getPageNumber());
        Assertions.assertEquals(0, response.getPageSize());
        Assertions.assertEquals(0L, response.getTotalElements());
        Assertions.assertEquals(0, response.getTotalPages());
        Assertions.assertFalse(response.isLast());
    }

}
