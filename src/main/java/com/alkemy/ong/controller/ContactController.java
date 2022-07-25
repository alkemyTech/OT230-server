package com.alkemy.ong.controller;

import com.alkemy.ong.models.request.ContactRequest;
import com.alkemy.ong.models.response.ContactResponse;
import com.alkemy.ong.service.ContactService;
import com.amazonaws.services.dynamodbv2.xspec.L;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.alkemy.ong.utils.ApiConstants.ROLE_USER;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

   private final ContactService contactService;

   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @GetMapping
   public ResponseEntity<List<ContactResponse>> listContacts(){
      return ResponseEntity.ok(contactService.listContacts());
   }
   @PostMapping
   public ResponseEntity<Void> addContact(@RequestBody @Valid ContactRequest request) throws IOException {
      contactService.addContact(request);
      return ResponseEntity.status(HttpStatus.CREATED).build();
   }


}
