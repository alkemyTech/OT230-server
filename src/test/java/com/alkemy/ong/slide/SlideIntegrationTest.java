package com.alkemy.ong.slide;

import com.alkemy.ong.context.SlideContexTest;
import com.alkemy.ong.models.entity.OrganizationEntity;
import com.alkemy.ong.models.entity.SlideEntity;
import com.alkemy.ong.models.request.SlideRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SlideIntegrationTest extends SlideContexTest {

    private final String URL_SLIDE = "/slides";



    //Happy path

    @Test
    public void shouldReturnOKStatusCodeAndAllSlideListWhenRoleAdminIsValid() throws Exception {
        // GET all
        mockMvc.perform(get(URL_SLIDE)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..imageUrl", notNullValue()))
                .andExpect(jsonPath("$..sort", notNullValue()))
                .andExpect(status().isOk());
    }


    @Test
    public void shouldReturnOKStatusCodeAndSlideDetailsByIdWhenRoleAdminIsValid() throws Exception {
        // GET slide details by id
        SlideEntity slide = createSlide();
        mockMvc.perform(get(URL_SLIDE + "/" + slide.getId())
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(slide.getId().intValue())))
                .andExpect(jsonPath("$.imageUrl", equalTo(slide.getImageUrl())))
                .andExpect(jsonPath("$.sort", equalTo(slide.getSort())))
                .andExpect(jsonPath("$.text", equalTo(slide.getText())))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOKStatusCodeAndListSlideByOrganizationIdWhenRoleUserIsValid() throws Exception {
        // GET list slides by organization id
        OrganizationEntity ong = createOrganization();
        SlideEntity slide = createSlide(ong.getId());

        mockMvc.perform(get(URL_SLIDE + "/organization/" + ong.getId())
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..id", notNullValue()))
                .andExpect(jsonPath("$..imageUrl", notNullValue()))
                .andExpect(jsonPath("$..sort", notNullValue()))
                .andExpect(jsonPath("$..text", notNullValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnCreatedStatusCodeAndSlideBodyWhenRoleAdminIsValid() throws Exception {
        //POST Slide
        mockMvc.perform(post(URL_SLIDE)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                Base64.URL,
                                "Slide Post test",
                                1,
                                3L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.imageUrl", notNullValue()))
                .andExpect(jsonPath("$.text", IsEqual.equalTo("Slide Post test")))
                .andExpect(jsonPath("$.organizationId", IsEqual.equalTo(3)))
                .andExpect(jsonPath("$.sort", equalTo(1)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnOKStatusCodeAndSlideBodyUpdatedWhenRoleAdminIsValid() throws Exception {
        //Put Slide
        SlideEntity slide = createSlide();
        mockMvc.perform(put(URL_SLIDE + "/" + slide.getId())
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .content(createRequest(
                                Base64.URL_UPDATE,
                                "Slide Post test update",
                                2,
                                2L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.imageUrl", notNullValue()))
                .andExpect(jsonPath("$.text", IsEqual.equalTo("Slide Post test update")))
                .andExpect(jsonPath("$.organizationId", IsEqual.equalTo(2)))
                .andExpect(jsonPath("$.sort", equalTo(2)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOKStatusCodeAndDeleteSlideMessageWhenRoleAdminIsValid() throws Exception {
        //Delete Slide
        SlideEntity slide = createSlide();
        mockMvc.perform(delete(URL_SLIDE + "/" + slide.getId())
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", equalTo("The slide was deleted correctly")))
                .andExpect(status().isOk());
    }

    //Error cases
    @Test
    public void shouldReturnNotFoundStatusCodeWhenGetSlideDetailsByIdNonExistAndRoleAdminIsValid() throws Exception {
        // GET slide details by id
        mockMvc.perform(get(URL_SLIDE + "/" + -1)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("No slide found with that id")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnForbiddenStatusCodeWhenGetSlideDetailsByIdAndRoleAdminIsInvalid() throws Exception {
        // GET slide details by id
        // Invalid role
        mockMvc.perform(get(URL_SLIDE + "/" + 1)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    private String createRequest(String urlBase64, String text, Integer sort, Long organizationId) throws JsonProcessingException {

        return objectMapper.writeValueAsString(SlideRequest.builder()
                        .imageUrl(urlBase64)
                        .text(text)
                        .sort(sort)
                        .organizationId(organizationId)
                        .build());
    }


}
