package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.ContactNotFoundException;
import com.alkemy.ong.exception.EmailExistsException;
import com.alkemy.ong.models.entity.ContactEntity;
import com.alkemy.ong.models.mapper.ContactMapper;
import com.alkemy.ong.models.request.ContactRequest;
import com.alkemy.ong.models.response.ContactResponse;
import com.alkemy.ong.repository.ContactsRepository;
import com.alkemy.ong.service.ContactService;
import com.alkemy.ong.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

   private final ContactsRepository contactsRepository;
   private final ContactMapper contactMapper;

   @Autowired
   private final EmailService emailService;

   @Override
   public void addContact(ContactRequest request) throws IOException {
      if (contactsRepository.existsByEmail(request.getEmail()))
         throw new EmailExistsException("There is a contact with the same email");

      ContactEntity contact = contactMapper.requestToContactEntity(request);
      contactsRepository.save(contact);
      try {
         emailService.switchEmail(contact.getEmail(), 2);
      } catch (IOException ex){
         throw ex;
      }
   }

   public List<ContactResponse> listContacts() throws ContactNotFoundException{
      List<ContactEntity> contacts = contactsRepository.findBySoftDelete();

      if (contacts.isEmpty()){
         throw new ContactNotFoundException("Not found contacts");
      }
      return contactMapper.entityToResponseList(contacts);
   }
}
