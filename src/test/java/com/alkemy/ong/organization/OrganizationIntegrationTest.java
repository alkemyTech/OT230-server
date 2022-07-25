package com.alkemy.ong.organization;

import com.alkemy.ong.context.OrganizationContextTest;
import com.alkemy.ong.models.entity.OrganizationEntity;
import com.alkemy.ong.models.request.OrganizationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.Test;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrganizationIntegrationTest extends OrganizationContextTest {

    private final String URL_ORGANIZATION = "/organizations";

    //Happy path

    @Test
    //Post with ROLE_ADMIN
    public void should_return_CREATED_status_code_when_Admin_create_new_organization() throws Exception {

        mockMvc.perform(post(URL_ORGANIZATION)
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(
                        "Somos Mas",
                        "una imagen",
                        "calle falsa 123",
                        12344321,
                        "somosmas@test.com",
                        "bienvenido!",
                        "somos un monton",
                        "somosmasfacebook",
                        "somosmasinstagram",
                        "somosmasIn"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", notNullValue()))
                .andExpect(jsonPath("$.image", notNullValue()))
                .andExpect(jsonPath("$.address", notNullValue()))
                .andExpect(jsonPath("$.phone", notNullValue()))
                .andExpect(jsonPath("$.email", notNullValue()))
                .andExpect(jsonPath("$.welcomeText", notNullValue()))
                .andExpect(jsonPath("$.aboutUsText", notNullValue()))
                .andExpect(jsonPath("$.urlFacebook", notNullValue()))
                .andExpect(jsonPath("$.urlInstagram", notNullValue()))
                .andExpect(jsonPath("$.urlLinkedin", notNullValue()))
                .andExpect(status().isCreated());
    }

    @Test
    //Get with ROLE_ADMIN
    public void should_return_OK_status_code_and_a_list_of_all_organizations_when_Admin_is_logged() throws Exception {

        mockMvc.perform(get(URL_ORGANIZATION + "/public")
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    //Get with ROLE_USER
    public void should_return_OK_status_code_and_a_list_of_all_organizations_when_User_is_logged() throws Exception {

        mockMvc.perform(get(URL_ORGANIZATION + "/public")
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    //Put with ROLE_ADMIN
    public void should_return_OK_status_code_and_the_organization_updated_when_Admin_is_logged() throws Exception {

        OrganizationEntity organization = createOrganization();
        mockMvc.perform(put(URL_ORGANIZATION + "/public/" + organization.getId())
        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
        .content(createRequest(
                "Somos Mas",
                "una imagen",
                "calle falsa 123",
                12344321,
                "somosmas@test.com",
                "bienvenido!",
                "somos un monton",
                "somosmasfacebook",
                "somosmasinstagram",
                "somosmasIn"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", notNullValue()))
                .andExpect(jsonPath("$.image", notNullValue()))
                .andExpect(jsonPath("$.address", notNullValue()))
                .andExpect(jsonPath("$.phone", notNullValue()))
                .andExpect(jsonPath("$.email", notNullValue()))
                .andExpect(jsonPath("$.welcomeText", notNullValue()))
                .andExpect(jsonPath("$.aboutUsText", notNullValue()))
                .andExpect(jsonPath("$.urlFacebook", notNullValue()))
                .andExpect(jsonPath("$.urlInstagram", notNullValue()))
                .andExpect(jsonPath("$.urlLinkedin", notNullValue()))
                .andExpect(status().isOk());
    }

    //Error Cases

    @Test
    //Post with ROLE_USER
    public void should_return_FORBIDDEN_status_code_when_try_create_organization_with_ROLE_USER() throws Exception {

        mockMvc.perform(post(URL_ORGANIZATION)
        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    //Post with Bad_Request_with_ROLE_ADMIN
    public void should_return_BAD_REQUEST_status_code_when_try_create_organization_without_mail_with_ROLE_ADMIN() throws Exception {

        mockMvc.perform(post(URL_ORGANIZATION)
        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
        .content(createRequest(
                "Somos Mas",
                "una imagen",
                "calle falsa 123",
                12344321,
                "",
                "bienvenido!",
                "somos un monton",
                "somosmasfacebook",
                "somosmasinstagram",
                "somosmasIn"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..name", notNullValue()))
                .andExpect(jsonPath("$..image", notNullValue()))
                .andExpect(jsonPath("$..address", notNullValue()))
                .andExpect(jsonPath("$..phone", notNullValue()))
                .andExpect(jsonPath("$..email", notNullValue()))
                .andExpect(jsonPath("$..welcomeText", notNullValue()))
                .andExpect(jsonPath("$..aboutUsText", notNullValue()))
                .andExpect(jsonPath("$..urlFacebook", notNullValue()))
                .andExpect(jsonPath("$..urlInstagram", notNullValue()))
                .andExpect(jsonPath("$..urlLinkedin", notNullValue()))
                .andExpect(status().isBadRequest());
    }

    @Test
    //Put an organization with ROLE_USER
    public void should_return_FORBIDDEN_status_code_when_try_update_an_organization_with_ROLE_USER() throws Exception {

        OrganizationEntity organization = createOrganization();
        mockMvc.perform(put(URL_ORGANIZATION + "/public/" + organization.getId())
        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    //Put with bad request with ROLE_ADMIN
    public void should_return_BAD_REQUEST_status_code_when_try_to_update_organization_with_ROLE_ADMIN() throws Exception {

        OrganizationEntity organization = createOrganization();
        mockMvc.perform(put(URL_ORGANIZATION + "/public/" + organization.getId())
        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(
                        "Somos Mas",
                        "una imagen",
                        "calle falsa 123",
                        12344321,
                        "",
                        "bienvenido!",
                        "somos un monton",
                        "somosmasfacebook",
                        "somosmasinstagram",
                        "somosmasIn"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..name", notNullValue()))
                .andExpect(jsonPath("$..image", notNullValue()))
                .andExpect(jsonPath("$..address", notNullValue()))
                .andExpect(jsonPath("$..phone", notNullValue()))
                .andExpect(jsonPath("$..email", notNullValue()))
                .andExpect(jsonPath("$..welcomeText", notNullValue()))
                .andExpect(jsonPath("$..aboutUsText", notNullValue()))
                .andExpect(jsonPath("$..urlFacebook", notNullValue()))
                .andExpect(jsonPath("$..urlInstagram", notNullValue()))
                .andExpect(jsonPath("$..urlLinkedin", notNullValue()))
                .andExpect(status().isBadRequest());
    }

    private String createRequest(String name, String image, String address, Integer phone,
                                 String email, String welcomeText, String aboutUsText,
                                 String urlFacebook, String urlInstagram, String urlLinkedin) throws JsonProcessingException {

        return objectMapper.writeValueAsString(OrganizationRequest.builder()
        .name(name)
        .image(image)
        .address(address)
        .phone(phone)
        .email(email)
        .welcomeText(welcomeText)
        .aboutUsText(aboutUsText)
        .urlFacebook(urlFacebook)
        .urlInstagram(urlInstagram)
        .urlLinkedin(urlLinkedin)
        .build());
    }
}
