package com.projects.aeroplannerrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
import com.projects.aeroplannerrestapi.util.AbstractContainerBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenRegisterRequest_whenRegister_thenReturnRegisteredUser() throws Exception {
        // given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("sample@email.com");
        registerRequest.setFullName("full name");
        registerRequest.setPassword("Password@123!");

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        // then
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(registerRequest.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(registerRequest.getFullName()));
    }
}
