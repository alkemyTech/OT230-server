package com.alkemy.ong.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CategoryRequest {
    @NotNull(message = "Name can't be null.")
    @NotBlank
    @NotEmpty(message = "Name can't be empty.")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "The name has to contain only letters")
    private String name;

    private String description;

    private String image;
}
