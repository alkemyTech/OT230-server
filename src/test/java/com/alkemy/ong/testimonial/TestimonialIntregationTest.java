package com.alkemy.ong.testimonial;

import com.alkemy.ong.context.TestimonialContextTest;
import com.alkemy.ong.models.entity.ContactEntity;
import com.alkemy.ong.models.entity.TestimonialEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TestimonialIntregationTest extends TestimonialContextTest {

    private final String URL_TESTIMONIAL = "/testimonials";

    @Test
    public void shouldReturnTestimonialsWhenRoleAdminIsValid() throws Exception {
        TestimonialEntity testimonial = createTestimonial();
        mockMvc.perform(get(URL_TESTIMONIAL + "/get-all")
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..testimonials", notNullValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTestimonialsWhenRoleStandardUserIsValid() throws Exception {
        TestimonialEntity testimonial = createTestimonial();
        mockMvc.perform(get(URL_TESTIMONIAL + "/get-all")
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..testimonials", notNullValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnCreatedStatusCodeAndTestimonialBodyWhenRoleAdminAreValid() throws Exception {

        mockMvc.perform(post(URL_TESTIMONIAL)
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(
                        "Testimonial post test",
                        "url_image",
                        "Content test"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", equalTo("Testimonial post test")))
                .andExpect(jsonPath("$.image", equalTo("url_image")))
                .andExpect(jsonPath("$.content", equalTo("Content test")))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_return_OK_status_code_when_ROLE_ADMIN_delete_a_testimonial() throws Exception {

        TestimonialEntity testimonial = createTestimonial();
        mockMvc.perform(delete(URL_TESTIMONIAL + "/" + testimonial.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_OK_status_code_when_ROLE_ADMIN_update_a_testimonial() throws Exception {

        TestimonialEntity testimonial = createTestimonial();
        mockMvc.perform(put(URL_TESTIMONIAL + "/" + testimonial.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(
                        "Testimonial post test",
                        "url_image",
                        "Content test"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", equalTo("Testimonial post test")))
                .andExpect(jsonPath("$.image", equalTo("url_image")))
                .andExpect(jsonPath("$.content", equalTo("Content test")))
                .andExpect(status().isOk());
    }

    //Error cases
    @Test
    public void shouldReturnNotFoundWhenRoleStandardUserIsValid() throws Exception {
        TestimonialEntity testimonial = createTestimonial();
        mockMvc.perform(get(URL_TESTIMONIAL + "/get-all").param("page", String.valueOf(-1))
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnForbiddenStatusCodeWhenRoleAdminIsInvalid() throws Exception {
        //Invalid role
        mockMvc.perform(post(URL_TESTIMONIAL)
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                .content(createRequest(
                        "Testimonial post test",
                        "url_image",
                        "Content test"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnBadRequestStatusCodeWhenRoleAdminIsValid() throws Exception {

        mockMvc.perform(post(URL_TESTIMONIAL)
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(
                        "",
                        "url_image",
                        "Content test"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestStatusCodeWhenNameIsNullAndRoleAdminIsValid() throws Exception {

        mockMvc.perform(post(URL_TESTIMONIAL)
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(
                        null,
                        "url_image",
                        "Content test"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestStatusCodeWhenUrlImageIsNullAndRoleAdminIsValid() throws Exception {

        mockMvc.perform(post(URL_TESTIMONIAL)
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(
                        "Name Testimonial",
                        null,
                        "Content test"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestStatusCodeWhenUrlImageIsBlankAndRoleAdminIsValid() throws Exception {

        mockMvc.perform(post(URL_TESTIMONIAL)
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(
                        "Name Testimonial",
                        "",
                        "Content test"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestStatusCodeWhenUrlContentIsNullAndRoleAdminIsValid() throws Exception {

        mockMvc.perform(post(URL_TESTIMONIAL)
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(
                        "Name Testimonial",
                        "url_image",
                        null))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestStatusCodeWhenUrlContentIsBlankAndRoleAdminIsValid() throws Exception {

        mockMvc.perform(post(URL_TESTIMONIAL)
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(
                        "Name Testimonial",
                        "url_image",
                        ""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_FORBIDDEN_status_code_when_try_delete_a_testimonial_with_ROLE_USER() throws Exception {

        TestimonialEntity testimonial = createTestimonial();
        mockMvc.perform(delete(URL_TESTIMONIAL + "/" + testimonial.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void should_return_NOT_FOUND_status_code_when_try_delete_a_not_existent_testimonialID() throws Exception {

        mockMvc.perform(delete(URL_TESTIMONIAL + "/1000")
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_FORBIDDEN_status_code_when_try_update_testimonial_with_ROLE_USER() throws Exception {

        TestimonialEntity testimonial = createTestimonial();
        mockMvc.perform(put(URL_TESTIMONIAL + "/" + testimonial.getId())
                .content(createRequest(
                        "Name Testimonial",
                        "url_image",
                        ""))
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_NOT_FOUND_status_code_when_testimonialId_is_not_existent_with_ROLE_ADMIN() throws Exception {

        mockMvc.perform(put(URL_TESTIMONIAL + "/1000")
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(
                        "Name Testimonial",
                        "url_image",
                        "test content"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
