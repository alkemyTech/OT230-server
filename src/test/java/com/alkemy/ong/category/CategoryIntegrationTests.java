package com.alkemy.ong.category;

import com.alkemy.ong.context.CategoryContextTests;
import com.alkemy.ong.models.entity.CategoryEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CategoryIntegrationTests extends CategoryContextTests {


    public final static String URL_CONTROLLER = "/categories";


    @Test
    public void shouldReturnCategoriesListWhenRoleAdminAreValid() throws Exception {

        mockMvc.perform(get(URL_CONTROLLER)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].name", notNullValue()))
                .andExpect(status().isOk());
    }
    @Test
    public void shouldReturnCategoriesPageWhenRoleAdminAreValid() throws Exception {

        mockMvc.perform(get(URL_CONTROLLER).param("page", String.valueOf(1))
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..categories", notNullValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnCategoryByIDWhenRoleAdminAreValid() throws Exception {

        CategoryEntity category = createCategory();

        mockMvc.perform(get(URL_CONTROLLER + "/" + category.getId())
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("Category Test")))
                .andExpect(jsonPath("$.description", equalTo("Description")))
                .andExpect(jsonPath("$.image", equalTo("url_image")))
                .andExpect(jsonPath("$.id", equalTo(category.getId().intValue())))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnCreatedStatusCodeAndCategoryBodyWhenRoleAdminAreValid() throws Exception {

        mockMvc.perform(post(URL_CONTROLLER)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                "Category Post test",
                                "Description",
                                "image_url"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("Category Post test")))
                .andExpect(jsonPath("$.description", equalTo("Description")))
                .andExpect(jsonPath("$.image", equalTo("image_url")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnOKStatusCodeAndCategoryPutBodyWhenRoleAdminAreValid() throws Exception {
        CategoryEntity category = createCategory();
        mockMvc.perform(put(URL_CONTROLLER + "/" + category.getId())
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                "Category test Modified",
                                "Description modified",
                                "image_url modified"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("Category test Modified")))
                .andExpect(jsonPath("$.description", equalTo("Description modified")))
                .andExpect(jsonPath("$.image", equalTo("image_url modified")))
                .andExpect(jsonPath("$.id", equalTo(category.getId().intValue())))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOKStatusCodeWhenRoleAdminAreValidAndDeleteCategory() throws Exception {
        CategoryEntity category = createCategory();
        mockMvc.perform(delete(URL_CONTROLLER + "/" + category.getId())
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundStatusCodeWhenPageNonExist() throws Exception {

        mockMvc.perform(get(URL_CONTROLLER).param("page", String.valueOf(-1))
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequestStatusCodeWhenRequestIsInvalid() throws Exception {

        mockMvc.perform(post(URL_CONTROLLER)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                null,
                                "Description",
                                "image_url"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void shouldReturnBadRequestStatusCodeWhenRequestNameNoContentLetters()throws Exception {

        mockMvc.perform(post(URL_CONTROLLER)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                "1234",
                                "Description",
                                "image_url"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnNotFoundStatusCodeWhenIdNonExist() throws Exception {

        mockMvc.perform(put(URL_CONTROLLER + "/" + -1)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                "Category test",
                                "Description",
                                "image_url"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNotFoundStatusCodeWhenTryDeleteCategoryAndIdNonExist() throws Exception {

        mockMvc.perform(delete(URL_CONTROLLER + "/" + -1)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    ////No permissions GET

    @Test
    public void shouldReturnForbiddenStatusCodeWhenRoleUserStandardAllCategories() throws Exception {

        mockMvc.perform(get(URL_CONTROLLER)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    @Test
    public void shouldReturnForbiddenStatusCodeWhenRoleIsStandardUserInCategoryPage() throws Exception {

        mockMvc.perform(get(URL_CONTROLLER).param("page", String.valueOf(1))
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnForbiddenStatusCodeWhenRoleIsStandardUser() throws Exception {

        mockMvc.perform(get(URL_CONTROLLER + "/" + 1L)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    //No permissions POST

    @Test
    public void shouldReturnForbiddenStatusCodeWhenRoleIsStandardUserTryPost() throws Exception {

        mockMvc.perform(post(URL_CONTROLLER)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                        .content(createRequest(
                                "Category Post test",
                                "Description",
                                "image_url"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    //No permissions PUT
    @Test
    public void shouldReturnForbiddenStatusCodeWhenRoleIsUserStandardTryPutCategory() throws Exception {

        mockMvc.perform(put(URL_CONTROLLER + "/" + 2L)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                        .content(createRequest(
                                "Category test Modified",
                                "Description modified",
                                "image_url modified"))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isForbidden());
    }
    //No permissions DELETE
    @Test
    public void shouldReturnForbiddenStatusCodeWhenRoleStandardUserTryDeleteCategory() throws Exception {
        mockMvc.perform(delete(URL_CONTROLLER + "/" + 1L)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }



    private String createRequest(String name, String description, String image) throws JsonProcessingException {

        return objectMapper.writeValueAsString(CategoryEntity.builder()
                .name(name)
                .description(description)
                .image(image)
                .build());
    }


}