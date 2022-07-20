package com.alkemy.ong.models.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlideRequest {

    private String imageUrl;
    private String text;
    private Integer sort;
    private Long organizationId;
}
