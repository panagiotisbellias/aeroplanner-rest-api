package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.RegisterUserDto;
import com.projects.aeroplannerrestapi.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto getAuthenticatedUser();

    List<UserDto> getAllUsers();

    UserDto createAdministrator(RegisterUserDto registerUserDto);
}
