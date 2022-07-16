package com.alkemy.ong.testing;

import com.alkemy.ong.context.MemberContextTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static com.alkemy.ong.context.ProfileTest.ADMIN_AUTH;
import static com.alkemy.ong.context.ProfileTest.USER_AUTH;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class MemberIntegrationTests extends MemberContextTests {

   @Autowired
   private MockMvc mockMvc;

   private static final String MEMBER_PATH = "/members";
   private static final String PAGE_PATH = "/members/get-all";
   private static final String ID_PATH = "/member/%d";

   @Test
   @WithUserDetails(ADMIN_AUTH)
   public void getAllMemberWithAdminAuth() throws Exception {
      mockMvc.perform(get(MEMBER_PATH))
         .andExpect(content().contentType(APPLICATION_JSON))
         .andExpect(jsonPath("$", hasSize(20)))
         .andExpect(status().isOk());
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void getAllMemberWithUserAuth() throws Exception {
      mockMvc.perform(get(MEMBER_PATH))
         .andDo(print())
         .andExpect(status().isForbidden());
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void getPageWithoutParamRequest() throws Exception {
      mockMvc.perform(get(PAGE_PATH))
         .andExpect(content().contentType(APPLICATION_JSON))
         .andDo(print())
         .andExpect(jsonPath("$.members", hasSize(10)))
         .andExpect(jsonPath("$.next", equalTo("/get-all?page=2")));
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void getPageWithParamRequest() throws Exception {
      mockMvc.perform(get(PAGE_PATH)
            .param("page", String.valueOf(2)))
         .andExpect(content().contentType(APPLICATION_JSON))
         .andDo(print())
         .andExpect(jsonPath("$.members", hasSize(10)))
         .andExpect(jsonPath("$.previous", equalTo("/get-all?page=1")));
   }

   @Test
   @WithUserDetails(ADMIN_AUTH)
   public void getPageWithoutAuth() throws Exception {
      mockMvc.perform(get(PAGE_PATH))
         .andDo(print())
         .andExpect(status().isForbidden());
   }
}
