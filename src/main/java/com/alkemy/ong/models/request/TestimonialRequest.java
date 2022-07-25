package com.alkemy.ong.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class TestimonialRequest {

    @NotBlank
    @NotNull(message = "the name can't be null")
    @NotEmpty(message = "the name can't be empty")
    private String name;

    @NotBlank
    @NotNull(message = "the image can't be null")
    @NotEmpty(message = "the image can't be empty")
    private String image;

    @NotBlank
    @NotNull(message = "the content can't be null")
    @NotEmpty(message = "the content can't be empty")
    private String content;
}
