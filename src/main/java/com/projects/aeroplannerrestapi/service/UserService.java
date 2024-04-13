package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.UserDto;
import com.projects.aeroplannerrestapi.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserDto getAuthenticatedUser();

    UserResponse getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);
}
