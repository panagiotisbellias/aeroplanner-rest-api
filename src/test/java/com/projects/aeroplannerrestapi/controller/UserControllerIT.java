package com.projects.aeroplannerrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.aeroplannerrestapi.entity.Role;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.RoleEnum;
import com.projects.aeroplannerrestapi.repository.RoleRepository;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.service.JwtService;
import com.projects.aeroplannerrestapi.util.AbstractContainerBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ActiveProfiles("integration")
@WithMockUser(roles = {"SUPER_ADMIN", "ADMIN"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT extends AbstractContainerBaseTest {

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

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Role savedRole;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        roleRepository.deleteAll();

        Role role = new Role();
        role.setName(RoleEnum.USER);
        role.setDescription("Default user role");
        savedRole = roleRepository.save(role);
    }

    @Test
    public void givenListOfAllUsers_whenGetAllUsers_thenReturnPaginatedAndSortedUsers() throws Exception {
        // given
        User user1 = new User();
        user1.setFullName("Full Name 1");
        user1.setEmail("sample1@email.com");
        user1.setPassword(passwordEncoder.encode("password1"));
        user1.setRoles(Set.of(savedRole));

        User user2 = new User();
        user2.setFullName("Full Name 2");
        user2.setEmail("sample2@email.com");
        user2.setPassword(passwordEncoder.encode("password 2"));
        user2.setRoles(Set.of(savedRole));

        List<User> savedUsers = userRepository.saveAll(List.of(user1, user2));

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/users")
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
    @WithMockUser(username = "sample@email.com")
    public void givenAuthenticatedUser_whenGetAuthenticatedUser_thenReturnAuthenticatedUser() throws Exception {
        // given
        String email = "sample@email.com";
        String password = "password";

        User authenticatedUser = new User();
        authenticatedUser.setFullName("Full Name");
        authenticatedUser.setEmail(email);
        authenticatedUser.setPassword(passwordEncoder.encode(password));
        authenticatedUser.setRoles(Set.of(savedRole));
        User savedUser = userRepository.save(authenticatedUser);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = jwtService.generateToken(savedUser);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/users/me")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(savedUser.getEmail()))
                .andExpect(jsonPath("$.fullName").value(savedUser.getFullName()));
    }
}
