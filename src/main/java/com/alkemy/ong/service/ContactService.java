package com.alkemy.ong.service;

import com.alkemy.ong.exception.ContactNotFoundException;
import com.alkemy.ong.models.request.ContactRequest;
import com.alkemy.ong.models.response.ContactResponse;

import java.io.IOException;
import java.util.List;

public interface ContactService {

   void addContact(ContactRequest request) throws IOException;

   public List<ContactResponse> listContacts() throws ContactNotFoundException;
}
