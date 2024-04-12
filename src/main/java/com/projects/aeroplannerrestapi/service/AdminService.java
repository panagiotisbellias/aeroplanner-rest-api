package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.RegisterUserDto;
import com.projects.aeroplannerrestapi.dto.UserDto;

import java.util.List;

public interface AdminService {

    UserDto createAdministrator(RegisterUserDto registerUserDto);

    List<UserDto> getAllAdministrators();

    UserDto getAdministrator(Long id);

    UserDto updateAdministrator(Long id, RegisterUserDto registerUserDto);

    void deleteAdministrator(Long id);
}
