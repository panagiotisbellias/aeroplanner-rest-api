package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.PassengerResponse;
import com.projects.aeroplannerrestapi.dto.UserDto;

public interface PassengerService {

    PassengerResponse getPassengers(int pageNumber, int pageSize, String sortBy, String sortDir);

    UserDto getPassenger(Long id);

    void deletePassenger(Long id);
}
