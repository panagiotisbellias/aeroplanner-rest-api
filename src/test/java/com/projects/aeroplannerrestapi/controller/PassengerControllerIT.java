package com.projects.aeroplannerrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PassengerControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Role role;

    @BeforeEach
    public void setup() {
        role = new Role();
        role.setName(RoleEnum.USER);
        role.setDescription("Default user role");

        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void givenListOfPassengers_whenGetPassengers_thenReturnPaginatedAndSortedPassengers() throws Exception {
        // given
        Role savedRole = roleRepository.save(role);

        User user1 = new User();
        user1.setFullName("Full Name 1");
        user1.setEmail("sample1@email.com");
        user1.setPassword(passwordEncoder.encode("password1"));
        Set<Role> roles1 = new HashSet<>();
        roles1.add(savedRole);
        user1.setRoles(roles1);

        User user2 = new User();
        user2.setFullName("Full Name 2");
        user2.setEmail("sample2@email.com");
        user2.setPassword(passwordEncoder.encode("password 2"));
        Set<Role> roles2 = new HashSet<>();
        roles2.add(savedRole);
        user2.setRoles(roles2);

        List<User> savedUsers = userRepository.saveAll(List.of(user1, user2));

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/passengers")
                .param("pageNum", "1")
                .param("pageSize", "10")
                .param("sortBy", "id")
                .param("sortDir", "asc"));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0][0].id").value(savedUsers.get(0).getId()))
                .andExpect(jsonPath("$.content[0][0].fullName").value(savedUsers.get(0).getFullName()))
                .andExpect(jsonPath("$.content[0][0].email").value(savedUsers.get(0).getEmail()))
                .andExpect(jsonPath("$.content[0][1].id").value(savedUsers.get(1).getId()))
                .andExpect(jsonPath("$.content[0][1].fullName").value(savedUsers.get(1).getFullName()))
                .andExpect(jsonPath("$.content[0][1].email").value(savedUsers.get(1).getEmail()));
    }

    @Test
    public void givenPassengerId_whenGetPassenger_thenReturnPassenger() throws Exception {
        // given
        Role savedRole = roleRepository.save(role);

        User user = new User();
        user.setFullName("Full Name");
        user.setEmail("sample@email.com");
        user.setPassword(passwordEncoder.encode("password"));
        Set<Role> roles1 = new HashSet<>();
        roles1.add(savedRole);
        user.setRoles(roles1);

        User savedUser = userRepository.save(user);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/passengers/{id}", savedUser.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(user.getFullName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }
}
