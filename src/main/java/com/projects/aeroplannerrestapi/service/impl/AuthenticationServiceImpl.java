package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.LoginResponse;
import com.projects.aeroplannerrestapi.dto.LoginUserDto;
import com.projects.aeroplannerrestapi.dto.RegisterUserDto;
import com.projects.aeroplannerrestapi.dto.UserDto;
import com.projects.aeroplannerrestapi.entity.Role;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.exception.UserAlreadyExistsException;
import com.projects.aeroplannerrestapi.mapper.UserMapper;
import com.projects.aeroplannerrestapi.repository.RoleRepository;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.service.AuthenticationService;
import com.projects.aeroplannerrestapi.service.JwtService;
import com.projects.aeroplannerrestapi.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistService tokenBlacklistService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserDto register(RegisterUserDto registerUserDto) {
        String email = registerUserDto.getEmail();
        boolean isUserExists = userRepository.existsByEmail(email);
        Optional<Role> role = roleRepository.findByName(RoleEnum.USER);
        if (role.isEmpty()) throw new ResourceNotFoundException("Role", "name", RoleEnum.USER.name());
        if (isUserExists) throw new UserAlreadyExistsException(email);
        User user = new User();
        user.setFullName(registerUserDto.getFullName());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(role.get());
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.userToUserDto(savedUser);
    }

    @Override
    public LoginResponse authenticate(LoginUserDto loginUserDto) {
        String email = loginUserDto.getEmail();
        String password = loginUserDto.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        String token = jwtService.generateToken(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setExpiredIn(jwtService.getExpirationTime());
        return loginResponse;
    }

    @Override
    public void logout(HttpServletRequest request) {
        String token = this.extractTokenFromRequest(request);
        tokenBlacklistService.addToBlacklist(token);
    }

    @Override
    public String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        // TODO: Create a custom exception
        return null;
    }
}
