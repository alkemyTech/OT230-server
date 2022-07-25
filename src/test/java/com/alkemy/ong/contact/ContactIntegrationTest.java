package com.alkemy.ong.contact;

import com.alkemy.ong.context.ContactContextTest;
import com.alkemy.ong.models.entity.ContactEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContactIntegrationTest extends ContactContextTest {

    public final static String URL_CONTACT = "/contacts";


    @Test
    public void shouldReturnContactListWhenRoleAdminIsValid() throws Exception {
        ContactEntity contact = createContact(EMAIL);
        mockMvc.perform(get(URL_CONTACT)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..name", notNullValue()))
                .andExpect(jsonPath("$..email", notNullValue()))
                .andExpect(jsonPath("$..phone", notNullValue()))
                .andExpect(jsonPath("$..message", notNullValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnCreatedStatusCodeAndContactBodyWhenRoleAdminAreValid() throws Exception {

        mockMvc.perform(post(URL_CONTACT)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                        .content(createRequest(
                                generateEmail(),
                                "Message POST Test"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnForbiddenStatusCodeWhenRoleAdminIsInvalid() throws Exception {
        ContactEntity contact = createContact(EMAIL);
        mockMvc.perform(get(URL_CONTACT)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnConflictStatusCodeWhenRoleAdminIsValid() throws Exception {

        mockMvc.perform(post(URL_CONTACT)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                        .content(createRequest(
                                EMAIL,
                                "Message POST Test"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("There is a contact with the same email")))
                .andExpect(status().isConflict());
    }

}
