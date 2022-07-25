package com.alkemy.ong.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityRequest {
    
    @NotNull
    @NotEmpty(message = "the name can't be null")
    @NotBlank(message = "the name can't  be blank")
    private String name;
    @NotNull
    @NotEmpty(message = "the content can't be null")
    @NotBlank(message = "the content can't  be blank")
    private String content;
    @NotNull
    @NotEmpty(message = "the image can't be null")
    @NotBlank(message = "the image can't  be blank")
    private String image;
}
