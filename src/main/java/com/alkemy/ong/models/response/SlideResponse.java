package com.alkemy.ong.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SlideResponse {

    private Long id;
    private String imageUrl;
    private String text;
    private Integer sort;
    private Long organizationId;
}
