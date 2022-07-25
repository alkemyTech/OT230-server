package com.alkemy.ong.models.mapper;

import com.alkemy.ong.exception.OrgNotFoundException;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.models.entity.CommentEntity;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.models.request.CommentRequest;
import com.alkemy.ong.models.response.CommentCompleteResponse;
import com.alkemy.ong.models.response.CommentResponse;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

    @Autowired
    NewsRepository newsRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    CommentRepository commentRepo;

    public CommentEntity toCommentEntity(CommentRequest commentReq) {
        return CommentEntity
                .builder()
                .body(commentReq.getBody())
                .newsId(commentReq.getNewsId())
                .userId(commentReq.getUserId())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    public CommentResponse toCommentResponse(CommentEntity commentEntity) {
        return CommentResponse
                .builder()
                .body(commentEntity.getBody())
                .build();
    }

    public CommentCompleteResponse toCommentCompleteResponse(CommentEntity commentEntity){
        return CommentCompleteResponse.builder()
                .commentId(commentEntity.getId())
                .userId(commentEntity.getUserId())
                .newsId(commentEntity.getNewsId())
                .body(commentEntity.getBody())
                .build();
    }

    public List<CommentResponse> CommentsResponseList(List<CommentEntity> comments) {
        List<CommentResponse> commentRespList = new ArrayList<>();
        comments.forEach( c -> {
            CommentResponse commentResp = CommentResponse
                    .builder()
                    .body(c.getBody())
                    .build();
            commentRespList.add(commentResp);
        });
        return commentRespList;
    }

    public CommentEntity changeValues(CommentRequest commentRequest, CommentEntity commentEntity){
        commentEntity.setNewsId(commentRequest.getNewsId());
        commentEntity.setUserId(commentRequest.getUserId());
        commentEntity.setBody(commentRequest.getBody());
        return commentEntity;
    }
}
