package com.alkemy.ong.activity;

import com.alkemy.ong.context.ActivityContextTest;
import com.alkemy.ong.models.entity.ActivityEntity;
import com.alkemy.ong.models.request.ActivityRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ActivityIntegrationTest extends ActivityContextTest {

    public final static String URL_CONTROLLER = "/activity";


    //Post success
    @Test
    public void shouldReturnCreatedStatusCodeAndActivityBodyWhenRoleAdminAreValid() throws Exception {

        mockMvc.perform(post(URL_CONTROLLER)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                "Activity Post test",
                                "Content",
                                "image_url"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("Activity Post test")))
                .andExpect(jsonPath("$.content", equalTo("Content")))
                .andExpect(jsonPath("$.image", equalTo("image_url")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(status().isCreated());
    }
    //Put success

    @Test
    public void shouldReturnOKStatusCodeAndActivityPutBodyWhenRoleAdminAreValid() throws Exception {
        ActivityEntity activity = createActivity();
        mockMvc.perform(put(URL_CONTROLLER + "/" + activity.getId())
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                "Activity test Modified",
                                "Content modified",
                                "image_url modified"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("Activity test Modified")))
                .andExpect(jsonPath("$.content", equalTo("Content modified")))
                .andExpect(jsonPath("$.image", equalTo("image_url modified")))
                .andExpect(jsonPath("$.id", equalTo(activity.getId().intValue())))
                .andExpect(status().isOk());
    }

    //POST Invalid

    @Test
    public void shouldReturnBadRequestStatusCodeWhenNameIsNull() throws Exception {

        mockMvc.perform(post(URL_CONTROLLER)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                null,
                                "Content",
                                "image_url"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestStatusCodeWhenContentIsNull() throws Exception {

        mockMvc.perform(post(URL_CONTROLLER)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                "Activity test",
                                null,
                                "image_url"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestStatusCodeWhenImageIsNull() throws Exception {

        mockMvc.perform(post(URL_CONTROLLER)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                "Activity test",
                                "Content",
                                null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestStatusCodeWhenAttributesAreBlank() throws Exception {

        mockMvc.perform(post(URL_CONTROLLER)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                "",
                                "",
                                ""))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // PUT Invalid

    @Test
    public void shouldReturnNotFoundStatusCodeWhenIDNonExist() throws Exception {

        mockMvc.perform(put(URL_CONTROLLER + "/" + -1)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                "Activity test Modified",
                                "Content modified",
                                "image_url modified"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("No activity found with that id")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequestStatusCodeWhenIDIsNull() throws Exception {

        mockMvc.perform(put(URL_CONTROLLER + "/" + null)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                "Activity test Modified",
                                "Content modified",
                                "image_url modified"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }




    private String createRequest(String name, String content, String image) throws JsonProcessingException {
        return objectMapper.writeValueAsString(ActivityRequest.builder()
                .name(name)
                .content(content)
                .image(image)
                .build());
    }
}
