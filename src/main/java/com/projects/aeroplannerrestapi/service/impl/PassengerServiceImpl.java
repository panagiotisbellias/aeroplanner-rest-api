package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedPassengerResponseResponse;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.mapper.UserMapper;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final UserRepository userRepository;

    @Override
    public PaginatedAndSortedPassengerResponseResponse getPassengers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortBy.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<User> page = userRepository.findByRole(RoleEnum.USER, pageable);
        List<UserResponse> passengers = page.getContent().stream()
                .map(UserMapper.INSTANCE::userToUserResponse)
                .collect(Collectors.toList());
        return (PaginatedAndSortedPassengerResponseResponse) PaginatedAndSortedPassengerResponseResponse.builder()
                .content(passengers)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .last(page.isLast())
                .build();
    }

    @Override
    public UserResponse getPassenger(Long id) {
        return userRepository.findByIdAndRolesName(id, RoleEnum.USER)
                .map(UserMapper.INSTANCE::userToUserResponse)
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
