package com.alkemy.ong.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;


@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class TestimonialResponse {

    private Long id;

    private String name;

    private String image;

    private String content;

    private Timestamp timestamp;

}
