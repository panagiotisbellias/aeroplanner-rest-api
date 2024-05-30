package com.projects.aeroplannerrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.aeroplannerrestapi.dto.request.LoginRequest;
import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Set;

import static com.projects.aeroplannerrestapi.constants.OpenApiConstants.LOGGED_OUT_SUCCESSFULLY;
import static com.projects.aeroplannerrestapi.constants.PathConstants.*;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.AUTHORIZATION;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.BEARER;
import static com.projects.aeroplannerrestapi.util.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ActiveProfiles(INTEGRATION)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private String fullName;

    private String email;

    private String password;

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

        fullName = FULL_NAME;
        email = VALID_EMAIL_ADDRESS;
        password = VALID_PASSWORD;
    }

    @Test
    public void givenRegisterRequest_whenRegister_thenReturnRegisteredUser() throws Exception {
        // given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .fullName(fullName)
                .email(email)
                .password(password)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post(API_V1_AUTH.concat(REGISTER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        // then
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(EMAIL_PATH).value(registerRequest.getEmail()))
                .andExpect(jsonPath(FULL_NAME_PATH).value(registerRequest.getFullName()));
    }

    @Test
    public void givenLoginRequest_whenAuthenticate_thenReturnLoginResponse() throws Exception {
        // given
        User user = User.builder()
                .fullName(fullName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .roles(Set.of(savedRole))
                .build();
        userRepository.save(user);

        LoginRequest loginRequest = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        User foundUser = userRepository.findByEmail(email).get();
        String token = jwtService.generateToken(foundUser);
        long expirationTime = jwtService.getExpirationTime();

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(API_V1_AUTH.concat(LOGIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        // then
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(TOKEN_PATH).value(token))
                .andExpect(jsonPath(EXPIRED_IN_PATH).value(expirationTime));
    }

    @Test
    public void givenHttServletRequest_whenLogout_thenReturnSuccessMessage() throws Exception {
        // given
        User user = User.builder()
                .fullName(fullName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .roles(Set.of(savedRole))
                .build();
        userRepository.save(user);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        User foundUser = userRepository.findByEmail(email).get();
        String token = jwtService.generateToken(foundUser);

        // when
        ResultActions resultActions = mockMvc.perform(post(API_V1_AUTH.concat(LOGOUT))
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, BEARER + token));

        // then
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(LOGGED_OUT_SUCCESSFULLY));
    }
}
