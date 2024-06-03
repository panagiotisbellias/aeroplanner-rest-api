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

import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_USERS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ME;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.*;
import static com.projects.aeroplannerrestapi.constants.SortingAndPaginationConstants.*;
import static com.projects.aeroplannerrestapi.util.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ActiveProfiles(INTEGRATION)
@WithMockUser(roles = {SUPER_ADMIN, ADMIN})
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

        Role role = Role.builder()
                .name(RoleEnum.USER)
                .description(DEFAULT_USER_ROLE)
                .build();

        savedRole = roleRepository.save(role);
    }

    @Test
    public void givenListOfAllUsers_whenGetAllUsers_thenReturnPaginatedAndSortedUsers() throws Exception {
        // given
        User user1 = User.builder()
                .fullName(FULL_NAME.concat(ONE))
                .email(VALID_EMAIL_ADDRESS)
                .password(passwordEncoder.encode(VALID_PASSWORD))
                .roles(Set.of(savedRole))
                .build();

        User user2 = User.builder()
                .fullName(FULL_NAME.concat(TWO))
                .email(VALID_EMAIL_ADDRESS)
                .password(passwordEncoder.encode(VALID_PASSWORD))
                .roles(Set.of(savedRole))
                .build();

        List<User> savedUsers = userRepository.saveAll(List.of(user1, user2));

        // when
        ResultActions resultActions = mockMvc.perform(get(API_V1_USERS)
                .param(PAGE_NUM, DEFAULT_PAGE_NUM)
                .param(PAGE_SIZE, DEFAULT_PAGE_SIZE)
                .param(SORT_BY, DEFAULT_SORT_BY)
                .param(SORT_DIR, DEFAULT_SORT_DIR));

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
    @WithMockUser(username = VALID_EMAIL_ADDRESS)
    public void givenAuthenticatedUser_whenGetAuthenticatedUser_thenReturnAuthenticatedUser() throws Exception {
        // given
        User authenticatedUser = User.builder()
                .fullName(FULL_NAME)
                .email(VALID_EMAIL_ADDRESS)
                .password(passwordEncoder.encode(VALID_PASSWORD))
                .roles(Set.of(savedRole))
                .build();

        User savedUser = userRepository.save(authenticatedUser);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(VALID_EMAIL_ADDRESS, VALID_PASSWORD));
        String token = jwtService.generateToken(savedUser);

        // when
        ResultActions resultActions = mockMvc.perform(get(API_V1_USERS.concat(ME))
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, BEARER + token));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(EMAIL_PATH).value(savedUser.getEmail()))
                .andExpect(jsonPath(FULL_NAME_PATH).value(savedUser.getFullName()));
    }
}
