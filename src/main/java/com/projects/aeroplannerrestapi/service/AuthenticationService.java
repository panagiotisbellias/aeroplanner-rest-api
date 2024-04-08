package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.LoginResponse;
import com.projects.aeroplannerrestapi.dto.LoginUserDto;
import com.projects.aeroplannerrestapi.dto.RegisterUserDto;
import com.projects.aeroplannerrestapi.dto.UserDto;

public interface AuthenticationService {

    UserDto register(RegisterUserDto registerUserDto);

    LoginResponse authenticate(LoginUserDto loginUserDto);
}
