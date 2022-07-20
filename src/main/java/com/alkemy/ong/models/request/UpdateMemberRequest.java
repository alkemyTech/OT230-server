package com.alkemy.ong.models.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class UpdateMemberRequest {

   @NotNull @NotEmpty @NotBlank
   @Pattern(regexp = "^[a-zA-Z]+$", message = "The name has to contain only letters")
   private String name;

   @NotNull @NotEmpty @NotBlank
   private String facebook;

   @NotNull @NotEmpty @NotBlank
   private String instagram;

   @NotNull @NotEmpty @NotBlank
   private String linkedIn;

   @NotNull @NotEmpty @NotBlank
   private String description;

   @NotNull @NotEmpty @NotBlank
   private String image;
}
