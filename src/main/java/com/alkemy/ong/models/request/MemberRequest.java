package com.alkemy.ong.models.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class MemberRequest {
   @ApiModelProperty(name = "Name",
           value = "Title of the Member",
           dataType = "String",
           notes = "It can't be null",
           example = "Greenpeace",
           required = true)
   @NotNull @NotEmpty @NotBlank
   @Pattern(regexp = "^[a-zA-Z]+$", message = "The name has to contain only letters")
   private String name;

   @ApiModelProperty(name = "Facebook",
           value = "Link of the Facebook profile",
           dataType = "String",
           notes = "It can't be null",
           example = "https://www.facebook.com/my-profile/",
           required = true)
   @NotEmpty @NotBlank
   private String facebook;

   @ApiModelProperty(name = "Instagram",
           value = "Link of the Instagram profile",
           dataType = "String",
           notes = "It can't be null",
           example = "https://www.instagram.com/my-profile/",
           required = true)
   @NotEmpty @NotBlank
   private String instagram;

   @ApiModelProperty(name = "Instagram",
           value = "Link of the Instagram profile",
           dataType = "String",
           notes = "It can't be null",
           example = "https://www.linkedin.com/my-profile/",
           required = true)
   @NotEmpty @NotBlank
   private String linkedIn;

   @ApiModelProperty(name = "Image",
           value = "Emblematic image of the member",
           dataType = "String",
           notes = "It can't be null",
           example = "image0001.jpg",
           required = true)
   @NotEmpty @NotBlank
   private String image;

   @ApiModelProperty(name = "Description",
           value = "Content of the description",
           dataType = "String",
           notes = "It can't be null",
           example = "Lorem Ipsum...",
           required = true)
   @NotEmpty @NotBlank
   private String description;
}
