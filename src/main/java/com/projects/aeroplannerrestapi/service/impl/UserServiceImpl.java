package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.UserDto;
import com.projects.aeroplannerrestapi.dto.UserResponse;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.mapper.UserMapper;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return UserMapper.INSTANCE.userToUserDto(currentUser);
    }

    @Override
    public UserResponse getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<User> page = userRepository.findAll(pageable);
        List<UserDto> content = page.getContent().stream()
                .map(UserMapper.INSTANCE::userToUserDto)
                .collect(Collectors.toList());
        UserResponse userResponse = new UserResponse();
        userResponse.setContent(content);
        userResponse.setPageNumber(page.getNumber());
        userResponse.setPageSize(page.getSize());
        userResponse.setTotalElements(page.getTotalElements());
        userResponse.setTotalPages(page.getTotalPages());
        userResponse.setLast(page.isLast());
        return userResponse;
    }
}
