package com.alkemy.ong.user;

import com.alkemy.ong.ContextTests;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.models.request.UserUpdateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserIntegrationTest extends ContextTests {

    private final static String URL_USER = "/users";

    //Happy path

    @Test
    //Get all users for ROLE_ADMIN
    public void should_return_OK_status_code_and_all_user_list_when_admin_is_logged() throws Exception {
        //getAllUsers

        mockMvc.perform(get(URL_USER + URL_USER)
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..firstName", notNullValue()))
                .andExpect(jsonPath("$..lastName", notNullValue()))
                .andExpect(jsonPath("$..email", notNullValue()))
                .andExpect(jsonPath("$..photo", notNullValue()))
                .andExpect(status().isOk());
    }

    @Test
    //Get pagination value = 1, for ROLE_ADMIN
    public void should_return_OK_status_code_when_param_is_1_and_a_list_of_ten_users_per_page_when_admin_is_logged() throws Exception {


        mockMvc.perform(get(URL_USER).param("page", String.valueOf(1))
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$..firstName", notNullValue()))
                .andExpect(jsonPath("$..lastName", notNullValue()))
                .andExpect(jsonPath("$..email", notNullValue()))
                .andExpect(jsonPath("$..photo", notNullValue()))
                .andExpect(status().isOk());
    }

    @Test
    //Get pagination value = null, for ROLE_ADMIN
    public void should_return_OK_status_code_and_a_list_of_ten_users_per_page_when_admin_is_logged() throws Exception {


        mockMvc.perform(get(URL_USER)
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$..firstName", notNullValue()))
                .andExpect(jsonPath("$..lastName", notNullValue()))
                .andExpect(jsonPath("$..email", notNullValue()))
                .andExpect(jsonPath("$..photo", notNullValue()))
                .andExpect(status().isOk());
    }

    @Test
    //Patch user with ROLE_ADMIN
    public void should_return_NO_CONTENT_status_code_patching_when_admin_is_logged() throws Exception {

        UserEntity user = saveAdminRandomEmail();
        mockMvc.perform(patch(URL_USER + "/" + user.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(
                        "Facu",
                        "Dalesio",
                        "12345678",
                        "fotito"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());
    }

    @Test
    //Delete user with ROLE_ADMIN
    public void should_return_NO_CONTENT_status_code_when_erasing_when_admin_is_logged() throws Exception {

        UserEntity user = saveAdminRandomEmail();
        mockMvc.perform(delete(URL_USER + "/" + user.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    //Error case

    @Test
    //Get all users with ROLE_USER
    public void should_return_FORBIDDEN_status_code_when_standard_user_is_logged_trying_getAllUsers() throws Exception {

        mockMvc.perform(get(URL_USER + URL_USER)
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    //Get pagination when insert negative number page with ROLE_ADMIN
    public void should_return_NOT_FOUND_status_code_when_admin_insert_negative_page_number() throws Exception {

        mockMvc.perform(get(URL_USER).param("page", String.valueOf(-1))
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    //Get pagination with ROLE_USER
    public void should_return_FORBIDDEN_status_code_when_try_get_a_list_of_users_per_page_when_standard_user_is_logged() throws Exception {

        mockMvc.perform(get(URL_USER).param("page", String.valueOf(1))
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    //Delete an user when insert a not existed userId with ROLE_ADMIN
    public void should_return_NOT_FOUND_status_code_when_admin_try_delete_a_not_existent_id() throws Exception {

        mockMvc.perform(delete(URL_USER + "/1000")
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    //Delete an user with ROLE_USER
    public void should_return_FORBIDDEN_status_code_when_User_try_delete_user() throws Exception {

        UserEntity user = saveStandardUserRandomEmail();
        mockMvc.perform(delete(URL_USER + "/" + user.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    //Patch an user when userId is not existent with ROLE_ADMIN
    public void should_return_NOT_FOUND_status_code_when_Admin_try_patch_a_not_existent_id() throws Exception {

        mockMvc.perform(patch(URL_USER + "/1000")
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(
                        "Facu",
                        "Dalesio",
                        "12345678",
                        "fotito"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    //Patch an user with ROLE_USER
    public void should_return_FORBIDDEN_status_code_when_standard_user_try_patch_an_user() throws Exception {

        UserEntity user = saveStandardUserRandomEmail();
        mockMvc.perform(patch(URL_USER + "/" + user.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                .content(createRequest(
                        "Facu",
                        "Dalesio",
                        "12345678",
                        "fotito"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    private String createRequest(String firstName, String lastName, String password, String photo) throws JsonProcessingException {
        return objectMapper.writeValueAsString(UserUpdateRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .photo(photo)
                .build());
    }

}
