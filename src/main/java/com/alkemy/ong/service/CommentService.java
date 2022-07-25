package com.alkemy.ong.service;

import com.alkemy.ong.models.request.CommentRequest;
import com.alkemy.ong.models.response.CommentCompleteResponse;
import com.alkemy.ong.models.response.CommentResponse;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface CommentService {
    List<CommentResponse> findAllComments();

    String removeComment(Long id, String token);

    List<CommentResponse> findCommentsByNews(Long id);

    CommentCompleteResponse create(CommentRequest commentRequest);

    CommentCompleteResponse update(Long id, CommentRequest commentRequest, String token);
}
