package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.RegisterUserDto;
import com.projects.aeroplannerrestapi.dto.UserDto;

public interface SuperAdminService {

    UserDto createAdministrator(RegisterUserDto registerUserDto);

    UserDto getAdministrator(Long id);

    UserDto updateAdministrator(Long id, RegisterUserDto registerUserDto);

    void deleteAdministrator(Long id);
}
