package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.constants.ErrorMessage;
import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedUserResponse;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.mapper.UserMapper;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Log LOG = LogFactory.getLog(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.USER,
                ErrorMessage.EMAIL, email));
        LOG.info(String.format("Current authenticated user : %s", user.getUsername()));
        return UserMapper.INSTANCE.userToUserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedAndSortedUserResponse getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<User> page = userRepository.findAll(pageable);
        List<UserResponse> users = page.getContent().stream()
                .map(UserMapper.INSTANCE::userToUserResponse)
                .toList();
        PaginatedAndSortedUserResponse userResponse = new PaginatedAndSortedUserResponse();
        userResponse.setContent(Collections.singletonList(users));
        userResponse.setPageNumber(page.getNumber());
        userResponse.setPageSize(page.getSize());
        userResponse.setTotalElements(page.getTotalElements());
        userResponse.setTotalPages(page.getTotalPages());
        userResponse.setLast(page.isLast());
        LOG.info(String.format("All users : %s", userResponse.getTotalElements()));
        return userResponse;
    }
}
