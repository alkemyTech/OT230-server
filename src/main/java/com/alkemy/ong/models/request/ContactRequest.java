package com.alkemy.ong.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ContactRequest {

   @NotNull @NotEmpty @NotBlank
   private String name;

   @NotNull @NotBlank @Email
   private String email;

   private String phone;

   private String message;
}
