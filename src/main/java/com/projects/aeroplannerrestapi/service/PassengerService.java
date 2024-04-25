package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedPassengerResponseResponse;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;

public interface PassengerService {

    PaginatedAndSortedPassengerResponseResponse getPassengers(int pageNumber, int pageSize, String sortBy, String sortDir);

    UserResponse getPassenger(Long id);

    void deletePassenger(Long id);
}
