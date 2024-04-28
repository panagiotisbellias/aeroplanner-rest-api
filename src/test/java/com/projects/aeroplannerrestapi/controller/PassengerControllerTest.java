package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedPassengerResponse;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.service.PassengerService;
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
class PassengerControllerTest {

    @InjectMocks
    PassengerController passengerController;

    @Mock
    PassengerService adminService;

    @Test
    void testConstructor() {
        PassengerController passengerController = new PassengerController(adminService);
        Assertions.assertInstanceOf(PassengerController.class, passengerController);
    }

    @Test
    void testGetPassengers() {
        ResponseEntity<PaginatedAndSortedPassengerResponse> response = passengerController.getPassengers(0, 1, "sort by", "sort dir");
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.OK, response);
    }

    @Test
    void testGetPassenger() {
        ResponseEntity<UserResponse> response = passengerController.getPassenger(0L);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.OK, response);
    }

    @Test
    void testDeletePassenger() {
        ResponseEntity<Void> response = passengerController.deletePassenger(0L);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.NO_CONTENT, response);
    }

}
