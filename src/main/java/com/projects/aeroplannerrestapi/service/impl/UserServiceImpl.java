package com.projects.aeroplannerrestapi.service.impl;

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
import com.projects.aeroplannerrestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return UserMapper.INSTANCE.userToUserDto(currentUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper.INSTANCE::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createAdministrator(RegisterUserDto registerUserDto) {
        String email = registerUserDto.getEmail();
        Optional<Role> role = roleRepository.findByName(RoleEnum.ADMIN);
        if (role.isEmpty()) throw new ResourceNotFoundException("Role", "name", RoleEnum.ADMIN.name());
        if (userRepository.existsByEmail(email)) throw new UserAlreadyExistsException(email);
        User user = new User();
        user.setFullName(registerUserDto.getFullName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setRole(role.get());
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.userToUserDto(savedUser);
    }
}
