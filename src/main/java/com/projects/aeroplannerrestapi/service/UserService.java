package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedUserResponseResponse;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;

public interface UserService {

    UserResponse getAuthenticatedUser();

    PaginatedAndSortedUserResponseResponse getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);
}
