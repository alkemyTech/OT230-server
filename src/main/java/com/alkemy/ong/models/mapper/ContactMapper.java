package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.ContactEntity;
import com.alkemy.ong.models.request.ContactRequest;
import com.alkemy.ong.models.response.ContactResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactMapper {

   public ContactEntity requestToContactEntity(ContactRequest request) {
      return ContactEntity.builder()
         .name(request.getName())
         .email(request.getEmail())
         .phone(request.getPhone())
         .message(request.getMessage())
         .build();
   }

   public ContactResponse entityToResponse(ContactEntity entity){
      return ContactResponse.builder()
              .id(entity.getId())
              .name(entity.getName())
              .email(entity.getEmail())
              .message(entity.getMessage())
              .phone(entity.getPhone())
              .build();
   }

   public List<ContactResponse> entityToResponseList(List<ContactEntity> entities){
      List<ContactResponse> responses = new ArrayList<>();
      for (ContactEntity entity:
           entities) {
         responses.add(entityToResponse(entity));
      }
      return responses;
   }
}
