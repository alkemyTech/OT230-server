package com.alkemy.ong.members;

import com.alkemy.ong.ContextTests;
import com.alkemy.ong.models.entity.MemberEntity;
import com.alkemy.ong.models.mapper.MemberMapper;
import com.alkemy.ong.models.request.MemberRequest;
import com.alkemy.ong.repository.MembersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class MemberContextTests extends ContextTests {

   @Autowired
   private MembersRepository repository;
   @Autowired
   private MemberMapper mapper;

   private static final ObjectMapper om = new ObjectMapper();

   protected void createUtilMember() {
      if(!repository.existsByName("equal")) buildAndSave("equal");
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
            .linkedIn( member.getLinkedinUrl() )
            .instagram( member.getInstagramUrl() )
            .facebook( member.getFacebookUrl() )
            .description(member.getDescription())
            .image(member.getImage())
            .build()
      );
   }

   //===========Utils===========//

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