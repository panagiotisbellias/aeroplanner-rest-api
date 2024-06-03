package com.projects.aeroplannerrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
import com.projects.aeroplannerrestapi.entity.Role;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import com.projects.aeroplannerrestapi.repository.RoleRepository;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.util.AbstractContainerBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;

import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_SUPER_ADMINS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ID;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.SUPER_ADMIN;
import static com.projects.aeroplannerrestapi.util.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(INTEGRATION)
@WithMockUser(roles = SUPER_ADMIN)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SuperAdminControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User user;

    private Role role;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        roleRepository.deleteAll();

        user = User.builder()
                .fullName(FULL_NAME)
                .email(VALID_EMAIL_ADDRESS)
                .password(passwordEncoder.encode(VALID_PASSWORD))
                .build();

        role = Role.builder()
                .name(RoleEnum.ADMIN)
                .description(ADMINISTRATOR_ROLE)
                .build();
    }

    @Test
    public void givenRegisterRequest_whenCreateAdministrator_thenReturnUserResponse() throws Exception {
        // given
        roleRepository.save(role);

        RegisterRequest registerRequest = RegisterRequest.builder()
                .email(VALID_EMAIL_ADDRESS)
                .password(VALID_PASSWORD)
                .fullName(FULL_NAME)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post(API_V1_SUPER_ADMINS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(EMAIL_PATH).value(registerRequest.getEmail()))
                .andExpect(jsonPath(FULL_NAME_PATH).value(registerRequest.getFullName()));
    }

    @Test
    public void givenAdminId_whenGetAdministrator_thenReturnUserResponse() throws Exception {
        // given
        Role savedRole = roleRepository.save(role);
        user.setRoles(Set.of(savedRole));
        User savedUser = userRepository.save(user);

        // when
        ResultActions resultActions = mockMvc.perform(get(API_V1_SUPER_ADMINS.concat(ID), savedUser.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(EMAIL_PATH).value(savedUser.getEmail()))
                .andExpect(jsonPath(FULL_NAME_PATH).value(savedUser.getFullName()));
    }

    @Test
    public void givenAdminIdAndRegisterRequest_whenUpdateAdministrator_thenReturnUserResponse() throws Exception {
        // given
        Role savedRole = roleRepository.save(role);
        user.setRoles(Set.of(savedRole));
        User savedUser = userRepository.save(user);

        RegisterRequest updatedRegisterRequest = RegisterRequest.builder()
                .email(UPDATED.concat(VALID_EMAIL_ADDRESS))
                .password(UPDATED.concat(VALID_PASSWORD))
                .fullName(UPDATED.concat(FULL_NAME))
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(put(API_V1_SUPER_ADMINS.concat(ID), savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRegisterRequest)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(EMAIL_PATH).value(updatedRegisterRequest.getEmail()))
                .andExpect(jsonPath(FULL_NAME_PATH).value(updatedRegisterRequest.getFullName()));
    }

    @Test
    public void givenAdminId_whenDeleteAdministrator_thenReturnNothing() throws Exception {
        // given
        Role savedRole = roleRepository.save(role);
        user.setRoles(Set.of(savedRole));
        User savedUser = userRepository.save(user);

        // when
        ResultActions resultActions = mockMvc.perform(delete(API_V1_SUPER_ADMINS.concat(ID), savedUser.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isNoContent());
    }
}
