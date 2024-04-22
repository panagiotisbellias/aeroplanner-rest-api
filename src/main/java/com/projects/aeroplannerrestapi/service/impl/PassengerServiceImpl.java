package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.UserDto;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.mapper.UserMapper;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getPassengers() {
        return userRepository.findByRoles_Name(RoleEnum.USER)
                .stream()
                .map(UserMapper.INSTANCE::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getPassenger(Long id) {
        return userRepository.findByIdAndRolesName(id, RoleEnum.USER)
                .map(UserMapper.INSTANCE::userToUserDto)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", "id", id.toString()));
    }

    @Override
    public void deletePassenger(Long id) {
        if (!userRepository.existsByIdAndRoles_Name(id, RoleEnum.USER)) {
            throw new ResourceNotFoundException("Passenger", "id", id.toString());
        }
        userRepository.deleteByIdAndRoles_Name(id, RoleEnum.USER);
    }
}
