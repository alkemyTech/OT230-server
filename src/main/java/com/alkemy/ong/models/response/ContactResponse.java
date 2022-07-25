package com.alkemy.ong.models.response;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactResponse {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private String message;
}
