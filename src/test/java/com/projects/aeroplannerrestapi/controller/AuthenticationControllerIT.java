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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
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

    @BeforeEach
    public void setup() {
        fullName = "full name";
        email = "sample@email.com";
        password = "Password@123!";

        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void givenRegisterRequest_whenRegister_thenReturnRegisteredUser() throws Exception {
        // given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFullName(fullName);
        registerRequest.setEmail(email);
        registerRequest.setPassword(passwordEncoder.encode(password));
        Role role = new Role();
        role.setName(RoleEnum.USER);
        role.setDescription("Default user role");
        roleRepository.save(role);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        // then
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(registerRequest.getEmail()))
                .andExpect(jsonPath("$.fullName").value(registerRequest.getFullName()));
    }

    @Test
    public void givenLoginRequest_whenAuthenticate_thenReturnLoginResponse() throws Exception {
        // given
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName(RoleEnum.USER);
        role.setDescription("Default user role");
        Role savedRole = roleRepository.save(role);
        roles.add(savedRole);
        user.setRoles(roles);
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User savedUser = userRepository.findByEmail(email).get();
        String token = jwtService.generateToken(savedUser);
        long expirationTime = jwtService.getExpirationTime();

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        // then
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.expiredIn").value(expirationTime));
    }

    @Test
    public void givenHttServletRequest_whenLogout_thenReturnSuccessMessage() throws Exception {
        // given
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName(RoleEnum.USER);
        role.setDescription("Default user role");
        Role savedRole = roleRepository.save(role);
        roles.add(savedRole);
        user.setRoles(roles);
        userRepository.save(user);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User savedUser = userRepository.findByEmail(email).get();
        String token = jwtService.generateToken(savedUser);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token));

        // then
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("Logged out successfully"));
    }
}
