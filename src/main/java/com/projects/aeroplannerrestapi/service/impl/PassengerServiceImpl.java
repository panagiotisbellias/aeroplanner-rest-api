package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedPassengerResponse;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.mapper.UserMapper;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.ID;
import static com.projects.aeroplannerrestapi.constants.ErrorMessage.PASSENGER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PassengerServiceImpl implements PassengerService {

    private static final Log LOG = LogFactory.getLog(PassengerServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public PaginatedAndSortedPassengerResponse getPassengers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<User> page = userRepository.findByRole(RoleEnum.USER, pageable);
        List<UserResponse> passengers = page.getContent().stream()
                .map(UserMapper.INSTANCE::userToUserResponse)
                .toList();
        PaginatedAndSortedPassengerResponse passengerResponse = new PaginatedAndSortedPassengerResponse();
        passengerResponse.setContent(Collections.singletonList(passengers));
        passengerResponse.setPageNumber(page.getNumber());
        passengerResponse.setPageSize(page.getSize());
        passengerResponse.setTotalPages(page.getTotalPages());
        passengerResponse.setTotalElements(page.getTotalElements());
        passengerResponse.setLast(page.isLast());
        LOG.info(String.format("Passengers : %s", passengerResponse.getTotalElements()));
        return passengerResponse;
    }

    @Override
    public UserResponse getPassenger(Long id) {
        return userRepository.findByIdAndRolesName(id, RoleEnum.USER)
                .map(UserMapper.INSTANCE::userToUserResponse)
                .orElseThrow(() -> new ResourceNotFoundException(PASSENGER, ID, id.toString()));
    }

    @Override
    @Transactional
    public void deletePassenger(Long id) {
        if (!userRepository.existsByIdAndRoles_Name(id, RoleEnum.USER)) {
            throw new ResourceNotFoundException(PASSENGER, ID, id.toString());
        }
        userRepository.deleteById(id);
        LOG.info(String.format("Passenger with id : %d is deleted", id));
    }
}
