package com.alkemy.ong.auth;

import com.alkemy.ong.ContextTests;
import com.alkemy.ong.models.request.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterIntegrationTests extends ContextTests {



    private static final String REGISTER_URL = "/auth/register";
    private final String EMAIL = generateEmail();

    @Test
    public void shouldReturnIsCreatedStatusCodeAndResourceWhenRequestAreValid() throws Exception {

        mockMvc.perform(post(REGISTER_URL)
                        .content(createRequest("Franco", "Toñanes", EMAIL, "12345678"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", equalTo("Franco")))
                .andExpect(jsonPath("$.lastName", equalTo("Toñanes")))
                .andExpect(jsonPath("$.email", equalTo(EMAIL)))
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(status().isCreated());
    }
    ///
    @Test
    public void shouldReturnIsBadRequestStatusCodeWhenFirstNameIsNull() throws Exception {

        mockMvc.perform(post(REGISTER_URL)
                        .content(createRequest(null,"Toñanes", EMAIL, "12345678"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", notNullValue()))
                .andExpect(status().isBadRequest());
    }

    private String createRequest(String firstName, String lastName, String email, String password) throws JsonProcessingException {
        return objectMapper.writeValueAsString(UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .build());
    }


}
