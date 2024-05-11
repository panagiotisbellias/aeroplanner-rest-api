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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private static final Log LOG = LogFactory.getLog(SuperAdminServiceImpl.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createAdministrator(RegisterRequest registerRequest) {
        LOG.debug(String.format("createAdministrator(%s)", registerRequest));
        String email = registerRequest.getEmail();
        Optional<Role> role = roleRepository.findByName(RoleEnum.ADMIN);
        if (role.isEmpty()) throw new ResourceNotFoundException(ROLE, NAME, RoleEnum.ADMIN.name());
        if (userRepository.existsByEmail(email)) throw new UserAlreadyExistsException(email);
        User user = new User();
        user.setFullName(registerRequest.getFullName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(role.get());
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        LOG.info("Administrator is registered");
        return UserMapper.INSTANCE.userToUserResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getAdministrator(Long id) {
        LOG.debug(String.format("getAdministrator(%d)", id));
        User user = findByIdAndRole(id);
        LOG.debug(String.format("Administrator %d : %s", id, user));
        return UserMapper.INSTANCE.userToUserResponse(user);
    }

    @Override
    public UserResponse updateAdministrator(Long id, RegisterRequest registerRequest) {
        LOG.debug(String.format("updateAdministrator(%d, %s)", id, registerRequest));
        User user = findByIdAndRole(id);
        user.setUpdatedAt(LocalDateTime.now());
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User updatedUser = userRepository.save(user);
        LOG.info(String.format("Administrator %d is updated", id));
        return UserMapper.INSTANCE.userToUserResponse(updatedUser);
    }

    @Override
    public void deleteAdministrator(Long id) {
        LOG.debug(String.format("deleteAdministrator(%d)", id));
        User user = findByIdAndRole(id);
        userRepository.delete(user);
        LOG.info(String.format("Administrator %d is deleted", id));
    }

    private User findByIdAndRole(Long id) {
        LOG.debug(String.format("findByIdAndRole(%d)", id));
        return userRepository.findByIdAndRolesName(id, RoleEnum.ADMIN)
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, id.toString()));
    }
}
