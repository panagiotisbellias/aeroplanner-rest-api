package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.constants.ErrorMessage;
import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedPassengerResponse;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.service.impl.PassengerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {

    @InjectMocks
    PassengerServiceImpl passengerService;

    @Mock
    UserRepository userRepository;

    @Mock
    Page<User> page;

    @Test
    void testConstructor() {
        PassengerService passengerService = new PassengerServiceImpl(userRepository);
        Assertions.assertInstanceOf(PassengerService.class, passengerService);
    }

    @Test
    void testGetPassengersAsc() {
        Mockito.when(userRepository.findByRole(ArgumentMatchers.eq(RoleEnum.USER), ArgumentMatchers.any(PageRequest.class))).thenReturn(page);
        PaginatedAndSortedPassengerResponse response = passengerService.getPassengers(1, 2, "asc", "sort dir");

        Assertions.assertEquals(1, response.getContent().size());
        Assertions.assertEquals(0, response.getPageNumber());
        Assertions.assertEquals(0, response.getPageSize());
        Assertions.assertEquals(0, response.getTotalPages());
        Assertions.assertEquals(0L, response.getTotalElements());
        Assertions.assertFalse(response.isLast());
    }

    @Test
    void testGetPassengersDesc() {
        Mockito.when(userRepository.findByRole(ArgumentMatchers.eq(RoleEnum.USER), ArgumentMatchers.any(PageRequest.class))).thenReturn(page);
        PaginatedAndSortedPassengerResponse response = passengerService.getPassengers(1, 2, "desc", "sort dir");

        Assertions.assertEquals(1, response.getContent().size());
        Assertions.assertEquals(0, response.getPageNumber());
        Assertions.assertEquals(0, response.getPageSize());
        Assertions.assertEquals(0, response.getTotalPages());
        Assertions.assertEquals(0L, response.getTotalElements());
        Assertions.assertFalse(response.isLast());
    }

    @Test
    void testGetPassenger() {
        User user = Mockito.mock(User.class);
        Mockito.when(userRepository.findByIdAndRolesName(0L, RoleEnum.USER)).thenReturn(Optional.of(user));

        UserResponse response = passengerService.getPassenger(0L);
        Assertions.assertNotNull(response);
    }

    @Test
    void testGetPassengerNotFound() {
        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> passengerService.getPassenger(0L));
        Assertions.assertEquals(ErrorMessage.PASSENGER.concat(" not found with ").concat(ErrorMessage.ID).concat(" : 0"), resourceNotFoundException.getMessage());
    }

    @Test
    void testDeletePassenger() {
        Mockito.when(userRepository.existsByIdAndRoles_Name(0L, RoleEnum.USER)).thenReturn(true);
        passengerService.deletePassenger(0L);

        Mockito.verify(userRepository).existsByIdAndRoles_Name(0L, RoleEnum.USER);
        Mockito.verify(userRepository).deleteByIdAndRoles_Name(0L, RoleEnum.USER);
    }

    @Test
    void testDeletePassengerNotFound() {
        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> passengerService.deletePassenger(0L));
        Assertions.assertEquals(ErrorMessage.PASSENGER.concat(" not found with ").concat(ErrorMessage.ID).concat(" : 0"), resourceNotFoundException.getMessage());
    }

}
