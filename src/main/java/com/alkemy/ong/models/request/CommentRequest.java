package com.alkemy.ong.models.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class CommentRequest {

    private String body;
    @NotNull(message = "NewsId can't be null")
    private Long newsId;
    @NotNull(message = "UserId can't be null")
    private Long userId;

}
