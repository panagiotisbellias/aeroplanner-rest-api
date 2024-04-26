package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.service.SuperAdminService;
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
class SuperAdminControllerTest {

    @InjectMocks
    SuperAdminController superAdminController;

    @Mock
    SuperAdminService superAdminService;

    @Mock
    RegisterRequest registerRequest;

    @Test
    void testConstructor() {
        SuperAdminController superAdminController = new SuperAdminController(superAdminService);
        Assertions.assertInstanceOf(SuperAdminController.class, superAdminController);
    }

    @Test
    void testCreateAdministrator() {
        ResponseEntity<UserResponse> response = superAdminController.createAdministrator(registerRequest);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.CREATED, response);
    }

    @Test
    void testGetAdministrator() {
        ResponseEntity<UserResponse> response = superAdminController.getAdministrator(0L);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.OK, response);
    }

    @Test
    void testUpdateAdministrator() {
        ResponseEntity<UserResponse> response = superAdminController.updateAdministrator(0L, registerRequest);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.OK, response);
    }

    @Test
    void testDeleteAdministrator() {
        ResponseEntity<Void> response = superAdminController.deleteAdministrator(0L);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.NO_CONTENT, response);
    }

}
