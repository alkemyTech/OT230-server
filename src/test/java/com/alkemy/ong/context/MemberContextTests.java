package com.alkemy.ong.context;

import com.alkemy.ong.models.entity.MemberEntity;
import com.alkemy.ong.models.mapper.MemberMapper;
import com.alkemy.ong.models.request.MemberRequest;
import com.alkemy.ong.repository.MembersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InMemoryUserDetails.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class MemberContextTests {

   @Autowired
   private MembersRepository repository;
   private MemberMapper mapper;
   private static final ObjectMapper om = new ObjectMapper();


   protected MemberEntity createUtilMember() {
      MemberEntity entity = repository.findByName("equal");
      return entity != null
         ? entity
         : buildAndSave("equal");
   }

   protected MemberEntity buildAndSave(String type) {
      return repository.save( buildMember(type, "instagram") );
   }

   protected MemberEntity buildMember(String type, String IG) {
      return MemberEntity.builder()
         .name( name(type) )
         .instagramUrl(IG)
         .linkedinUrl("linkedIn")
         .facebookUrl("facebook")
         .image("image")
         .description("description")
         .softDelete(false)
         .build();
   }

   protected String createRequest(MemberEntity member) throws JsonProcessingException {
      return om.writeValueAsString(
         MemberRequest.builder()
            .name(member.getName())
            .linkedIn(member.getLinkedinUrl())
            .instagram(member.getInstagramUrl())
            .facebook(member.getFacebookUrl())
            .description(member.getDescription())
            .image(member.getImage())
            .build()
      );
   }

   protected MemberEntity findById(Long id) {
      return repository.findById(id).orElseThrow(() ->  new EntityNotFoundException("Member not found with the id " + id));
   }

   private String name(String type) {
      switch (type) {
         case "name" : return randomName();

         case "equal" : return "equal";

         default: return "1234-?/";
      }
   }

   private String randomName() {
      return RandomStringUtils.randomAlphabetic(15);
   }

}
