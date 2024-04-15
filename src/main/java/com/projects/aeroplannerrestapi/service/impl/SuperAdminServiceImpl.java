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
import com.projects.aeroplannerrestapi.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        Set<Role> roles = new HashSet<>();
        roles.add(role.get());
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.userToUserDto(savedUser);
    }

    @Override
    public UserDto getAdministrator(Long id) {
        User user = findByIdAndRole(id, RoleEnum.ADMIN);
        return UserMapper.INSTANCE.userToUserDto(user);
    }

    @Override
    public UserDto updateAdministrator(Long id, RegisterUserDto registerUserDto) {
        User user = findByIdAndRole(id, RoleEnum.ADMIN);
        user.setUpdatedAt(LocalDateTime.now());
        user.setEmail(registerUserDto.getEmail());
        user.setFullName(registerUserDto.getFullName());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        User updatedUser = userRepository.save(user);
        return UserMapper.INSTANCE.userToUserDto(updatedUser);
    }

    @Override
    public void deleteAdministrator(Long id) {
        User user = findByIdAndRole(id, RoleEnum.ADMIN);
        userRepository.delete(user);
    }

    private User findByIdAndRole(Long id, RoleEnum roleEnum) {
        return userRepository.findByIdAndRolesName(id, roleEnum)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
    }
}
