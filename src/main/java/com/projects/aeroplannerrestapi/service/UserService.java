package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedUserResponse;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;

public interface UserService {

    UserResponse getAuthenticatedUser();

    PaginatedAndSortedUserResponse getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);
}
