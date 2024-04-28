package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedUserResponse;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.service.UserService;
import com.projects.aeroplannerrestapi.util.AssertionsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Test
    void testConstructor() {
        UserController userController = new UserController(userService);
        Assertions.assertInstanceOf(UserController.class, userController);
    }

    @Test
    void testGetAllAdministrators() {
        ResponseEntity<PaginatedAndSortedUserResponse> response = userController.getAllAdministrators(0, 1, "sort by", "sort dir");
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.OK, response);
    }

    @Test
    void testGetAuthenticatedUser() {
        ResponseEntity<UserResponse> response = userController.getAuthenticatedUser();
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.OK, response);
    }

}
