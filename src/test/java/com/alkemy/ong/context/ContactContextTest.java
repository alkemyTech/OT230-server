package com.alkemy.ong.context;

import com.alkemy.ong.ContextTests;
import com.alkemy.ong.models.entity.ContactEntity;
import com.alkemy.ong.repository.ContactsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ContactContextTest extends ContextTests {

    @Autowired
    private ContactsRepository contactsRepository;

    protected final String EMAIL = "contact@test.com";
    protected final String NAME = "Marcelo Olmedo";

    protected final String PHONE = "365486155";

    protected ContactEntity createContact(String email){
        if (contactsRepository.existsByEmail(email)){
            return contactsRepository.findByEmail(email);
        }
        return contactsRepository.save(ContactEntity
                        .builder()
                        .email(EMAIL)
                        .name(NAME)
                        .phone(PHONE)
                        .message("Mensaje Test")
                .build());
    }

    protected String createRequest(String email, String message) throws JsonProcessingException {

        return objectMapper.writeValueAsString(ContactEntity.builder()
                .email(email)
                .name(NAME)
                .phone(PHONE)
                .message(message)
                .build());
    }


}
