package com.alkemy.ong.auth;

import com.alkemy.ong.ContextTests;
import com.alkemy.ong.auth.utility.RoleEnum;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.models.request.AuthRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Test;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationTestIntegrationTests extends ContextTests {


    private static final String AUTH_LOGIN_URL = "/auth/login";

    @Test
    public void shouldReturnTokenWhenCredentialsAreValid() throws Exception {

        mockMvc.perform(post(AUTH_LOGIN_URL)
                        .content(createRequest("admin@authtest.com", "12345678"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", equalTo("admin@authtest.com")))
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnIsOkStatusCodeAndFalseWhenPasswordAreInvalid() throws Exception {
        mockMvc.perform(post(AUTH_LOGIN_URL)
                        .content(createRequest("admin@authtest.com", "invalidPass"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ok", equalTo(false)))
                .andExpect(status().isOk());
    }
    @Test
    public void shouldReturnIsOkStatusCodeAndFalseWhenEmailDoesNotExist() throws Exception {
        mockMvc.perform(post(AUTH_LOGIN_URL)
                        .content(createRequest("adm@test.com", "12345678"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ok", equalTo(false)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnIsBadRequestStatusCodeWhenEmailAreInvalid() throws Exception {
        mockMvc.perform(post(AUTH_LOGIN_URL)
                        .content(createRequest("invalidEmail", "12345678"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", equalTo("Email is not valid")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnIsOkStatusCodeAndAdminInformationWhenTokenAreValid() throws Exception {
        UserEntity user = saveAdminRandomEmail();
        mockMvc.perform(get("/auth/me")
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser(user.getEmail()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", equalTo(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(user.getLastName())))
                .andExpect(jsonPath("$.email", equalTo(user.getEmail())))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnIsOkStatusCodeAndStandardUserInformationWhenTokenAreValid() throws Exception {

        UserEntity user = saveStandardUserRandomEmail();

        mockMvc.perform(get("/auth/me")
                        .header(HttpHeaders.AUTHORIZATION,
                                BEARER + getAuthorizationTokenForStandardUser(user.getEmail()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", equalTo(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(user.getLastName())))
                .andExpect(jsonPath("$.email", equalTo(user.getEmail())))
                .andExpect(status().isOk());
    }

    private String createRequest(String email, String password) throws JsonProcessingException {

        return objectMapper.writeValueAsString(AuthRequest.builder()
                .email(email)
                .password(password)
                .build());
    }
}
