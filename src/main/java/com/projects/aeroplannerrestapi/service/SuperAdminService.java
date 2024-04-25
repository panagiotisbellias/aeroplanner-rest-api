package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;

public interface SuperAdminService {

    UserResponse createAdministrator(RegisterRequest registerRequest);

    UserResponse getAdministrator(Long id);

    UserResponse updateAdministrator(Long id, RegisterRequest registerRequest);

    void deleteAdministrator(Long id);
}
