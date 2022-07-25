package com.alkemy.ong.news;

import com.alkemy.ong.context.NewsContextTest;
import com.alkemy.ong.models.entity.CommentEntity;
import com.alkemy.ong.models.entity.NewsEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 class NewsIntegrationTests extends NewsContextTest {

    public final static String URL_CONTROLLER = "/news";

    @Test
    public void shouldReturnNewsListPageWhenAdminRoleAreValid() throws Exception{

        mockMvc.perform(get(URL_CONTROLLER).param("page", String.valueOf(1))
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..news", notNullValue()))
                .andExpect(status().isOk());
    }

     @Test
     public void shouldReturnNewsListPageWhenStandardRoleAreValid() throws Exception{

         mockMvc.perform(get(URL_CONTROLLER).param("page", String.valueOf(1))
                         .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$..news", notNullValue()))
                 .andExpect(status().isOk());
     }

     @Test
     public void shouldReturnNewsListWhenAdminRoleAreValid() throws Exception{

         mockMvc.perform(get(URL_CONTROLLER)
                         .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$..news", notNullValue()))
                 .andExpect(status().isOk());
     }

    @Test
     public void shouldReturnCreatedStatusAndNewsResponseWhenRoleAdminAreValid() throws Exception{
        NewsEntity news = createNewsParamsNoSave();

        mockMvc.perform(post(URL_CONTROLLER).header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(news.getName(), news.getContent(), news.getImage(), 1L))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo(news.getName())))
                .andExpect(jsonPath("$.content", equalTo(news.getContent())))
                .andExpect(jsonPath("$.image", equalTo(news.getImage())))
                .andExpect(status().isCreated());

    }

     @Test
     public void shouldReturnForbiddenStatusWhenRoleAdminAreNotValidInCreate() throws Exception{

         mockMvc.perform(post(URL_CONTROLLER).header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                         .content(createRequest("news test", "content test", "imageTest", 1L))
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isForbidden());

     }

     @Test
     public void shouldReturnBadRequestInCreateWhenRoleAdminAreValid() throws Exception{

         mockMvc.perform(post(URL_CONTROLLER).header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                         .content(createRequest(null, "content test", "imageTest", 1L))
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isBadRequest());

     }

    @Test
    public void shouldReturnNewsByIdWhenRoleAdminAreValid() throws Exception{
        NewsEntity news = createNewsParams();

        mockMvc.perform(get(URL_CONTROLLER + "/" + news.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo(news.getName())))
                .andExpect(jsonPath("$.content", equalTo(news.getContent())))
                .andExpect(jsonPath("$.image", equalTo(news.getImage())))
                .andExpect(jsonPath("$.categoryId", equalTo(news.getCategoryId().intValue())))
                .andExpect(status().isOk());

    }

     @Test
     public void shouldReturnNewsByIdWhenRoleStandarAreValid() throws Exception{
         NewsEntity news = createNewsParams();

         mockMvc.perform(get(URL_CONTROLLER + "/" + news.getId())
                         .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$.name", equalTo(news.getName())))
                 .andExpect(jsonPath("$.content", equalTo(news.getContent())))
                 .andExpect(jsonPath("$.image", equalTo(news.getImage())))
                 .andExpect(jsonPath("$.categoryId", equalTo(news.getCategoryId().intValue())))
                 .andExpect(status().isOk());

     }

    @Test
    public void shouldReturnNotFoundWhenIdNoExist() throws Exception{
        mockMvc.perform(get(URL_CONTROLLER + "/1000")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnOkStatusAndNewsResponseUpdateWhenRoleAdminAreValid() throws Exception{
        NewsEntity news = createNewsParams();
        NewsEntity newsUpdate = createNewsParamsNoSave();
        mockMvc.perform(put(URL_CONTROLLER + "/" + news.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(newsUpdate.getName(), newsUpdate.getContent(), newsUpdate.getImage(), 2L))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo(newsUpdate.getName())))
                .andExpect(jsonPath("$.content", equalTo(newsUpdate.getContent())))
                .andExpect(jsonPath("$.image", equalTo(newsUpdate.getImage())))
                .andExpect(status().isOk());
    }

     @Test
     public void shouldReturnNotFoundWhenIdUpdateNotExist() throws Exception{
         mockMvc.perform(put(URL_CONTROLLER + "/10000")
                         .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                         .content(createRequest("news update", "content update", "image update", 2L))
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isNotFound());
     }

     @Test
     public void shouldReturnForbiddenStatusWhenRoleAdminAreNotValidInUpdate() throws Exception{
         NewsEntity news = createNewsParams();
         mockMvc.perform(put(URL_CONTROLLER + "/" + news.getId())
                         .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                         .content(createRequest("news update", "content update", "image update", 2L))
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isForbidden());
     }

    @Test
    public void shouldReturnOkStatusWhenAdminRoleAreValidDeleteNews() throws Exception{
        NewsEntity news = createNewsParams();

        mockMvc.perform(delete(URL_CONTROLLER + "/" + news.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

     @Test
     public void shouldReturnNotFoundStatusWhenIdNotExistInDeleteNews() throws Exception{
         mockMvc.perform(delete(URL_CONTROLLER + "/1000")
                         .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isNotFound());
     }

     @Test
     public void shouldReturnForbiddenStatusWhenAdminRoleAreNotValidDeleteNews() throws Exception{
         NewsEntity news = createNewsParams();

         mockMvc.perform(delete(URL_CONTROLLER + "/" + news.getId())
                         .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isForbidden());
     }

    @Test
    public void shouldReturnCommentsListAndStatusOk() throws Exception{
        NewsEntity news = createNewsParams();
        CommentEntity comment = createComment(news);

        mockMvc.perform(get(URL_CONTROLLER + "/" + news.getId() + "/comments")
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..comment", notNullValue()))
                .andExpect(status().isOk());

    }

     private String createRequest(String name, String content, String image, Long categoryId) throws JsonProcessingException {

         return objectMapper.writeValueAsString(NewsEntity.builder()
                 .name(name)
                 .content(content)
                 .image(image)
                 .categoryId(categoryId)
                 .build());
     }
}
