package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
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
    public UserResponse createAdministrator(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        Optional<Role> role = roleRepository.findByName(RoleEnum.ADMIN);
        if (role.isEmpty()) throw new ResourceNotFoundException("Role", "name", RoleEnum.ADMIN.name());
        if (userRepository.existsByEmail(email)) throw new UserAlreadyExistsException(email);
        User user = new User();
        user.setFullName(registerRequest.getFullName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(role.get());
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.userToUserResponse(savedUser);
    }

    @Override
    public UserResponse getAdministrator(Long id) {
        User user = findByIdAndRole(id, RoleEnum.ADMIN);
        return UserMapper.INSTANCE.userToUserResponse(user);
    }

    @Override
    public UserResponse updateAdministrator(Long id, RegisterRequest registerRequest) {
        User user = findByIdAndRole(id, RoleEnum.ADMIN);
        user.setUpdatedAt(LocalDateTime.now());
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User updatedUser = userRepository.save(user);
        return UserMapper.INSTANCE.userToUserResponse(updatedUser);
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
