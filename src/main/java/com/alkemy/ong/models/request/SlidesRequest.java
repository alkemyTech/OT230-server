package com.alkemy.ong.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlidesRequest {

    private Long id;
    private String imageUrl;
    private String text;
    private Integer sort;
    private Long organizationId;
}