package com.alkemy.ong.models.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentCompleteResponse {

    private Long commentId;
    private Long userId;
    private Long newsId;
    private String body;
}
