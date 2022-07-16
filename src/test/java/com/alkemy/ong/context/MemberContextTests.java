package com.alkemy.ong.context;

import com.alkemy.ong.ContextTests;
import com.alkemy.ong.models.entity.MemberEntity;
import com.alkemy.ong.repository.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class MemberContextTests extends ContextTests {

   @Autowired
   private MembersRepository membersRepository;

   protected MemberEntity buildMember() {
      return membersRepository.save(
         MemberEntity.builder()
            .name("member")
            .facebookUrl("facebook")
            .description("description")
            .image("image")
            .softDelete(false)
            .build()
      );
   }
}
