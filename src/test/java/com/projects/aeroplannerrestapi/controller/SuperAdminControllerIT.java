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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("integration")
@WithMockUser(roles = "SUPER_ADMIN")
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

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void givenRegisterRequest_whenCreateAdministrator_thenReturnUserResponse() throws Exception {
        // given
        Role role = new Role();
        role.setName(RoleEnum.ADMIN);
        role.setDescription("Administrator role");
        roleRepository.save(role);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("sample@email.com");
        registerRequest.setPassword("password");
        registerRequest.setFullName("Full Name");

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/super-admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(registerRequest.getEmail()))
                .andExpect(jsonPath("$.fullName").value(registerRequest.getFullName()));
    }

    @Test
    public void givenAdminId_whenGetAdministrator_thenReturnUserResponse() throws Exception {
        // given
        User user = new User();
        user.setFullName("Full Name");
        user.setEmail("sample@email.com");
        user.setPassword(passwordEncoder.encode("password"));
        Role role = new Role();
        role.setName(RoleEnum.ADMIN);
        role.setDescription("Administrator role");
        Role savedRole = roleRepository.save(role);
        user.setRoles(Set.of(savedRole));
        User savedUser = userRepository.save(user);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/super-admins/{id}", savedUser.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(savedUser.getEmail()))
                .andExpect(jsonPath("$.fullName").value(savedUser.getFullName()));
    }

    @Test
    public void givenAdminIdAndRegisterRequest_whenUpdateAdministrator_thenReturnUserResponse() throws Exception {
        // given
        User user = new User();
        user.setFullName("Full Name");
        user.setEmail("sample@email.com");
        user.setPassword(passwordEncoder.encode("password"));
        Role role = new Role();
        role.setName(RoleEnum.ADMIN);
        role.setDescription("Administrator role");
        Role savedRole = roleRepository.save(role);
        user.setRoles(Set.of(savedRole));
        User savedUser = userRepository.save(user);

        RegisterRequest updatedRegisterRequest = new RegisterRequest();
        updatedRegisterRequest.setEmail("updated@email.com");
        updatedRegisterRequest.setPassword("updated password");
        updatedRegisterRequest.setFullName("Updated Full Name");

        // when
        ResultActions resultActions = mockMvc.perform(put("/api/v1/super-admins/{id}", savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRegisterRequest)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(updatedRegisterRequest.getEmail()))
                .andExpect(jsonPath("$.fullName").value(updatedRegisterRequest.getFullName()));
    }

    @Test
    public void givenAdminId_whenDeleteAdministrator_thenReturnNothing() throws Exception {
        // given
        User user = new User();
        user.setFullName("Full Name");
        user.setEmail("sample@email.com");
        user.setPassword(passwordEncoder.encode("password"));
        Role role = new Role();
        role.setName(RoleEnum.ADMIN);
        role.setDescription("Administrator role");
        Role savedRole = roleRepository.save(role);
        user.setRoles(Set.of(savedRole));
        User savedUser = userRepository.save(user);

        // when
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/super-admins/{id}", savedUser.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isNoContent());
    }
}
