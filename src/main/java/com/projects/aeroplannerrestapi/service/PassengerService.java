package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.UserDto;

import java.util.List;

public interface PassengerService {

    List<UserDto> getPassengers();

    UserDto getPassenger(Long id);

    void deletePassenger(Long id);
}
