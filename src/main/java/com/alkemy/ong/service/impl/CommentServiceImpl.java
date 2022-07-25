package com.alkemy.ong.service.impl;

import com.alkemy.ong.auth.service.JwtUtils;
import com.alkemy.ong.auth.service.impl.UserDetailsServiceImpl;
import com.alkemy.ong.exception.OrgNotFoundException;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.models.entity.CommentEntity;
import com.alkemy.ong.models.mapper.CommentMapper;
import com.alkemy.ong.models.request.CommentRequest;
import com.alkemy.ong.models.response.CommentCompleteResponse;
import com.alkemy.ong.models.response.CommentResponse;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepo, CommentMapper commentMapper) {
        this.commentRepo = commentRepo;
        this.commentMapper = commentMapper;
    }

    @Autowired
    UserRepository userRepo;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetails;

    @Override
    public List<CommentResponse> findAllComments() {
        List<CommentEntity> commentsList = commentRepo.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
        return commentMapper.CommentsResponseList(commentsList);
    }

    @Override
    public String removeComment(Long id, String token) {
        var commentDB = commentRepo.findById(id).orElseThrow(() -> new OrgNotFoundException("Comment not found"));

        var tokenSplit = token.split(" ");
        var username = jwtUtils.extractUsername(tokenSplit[1]);
        var user = userDetails.loadUserByUsername(username);
        var role = user.getAuthorities().iterator().next().getAuthority();

        var userDB = userRepo.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (role.equalsIgnoreCase("ROLE_ADMIN") | commentDB.getUser().equals(userDB)) {
            commentRepo.deleteById(id);
            return "Successfully removed";
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    @Override
    public List<CommentResponse> findCommentsByNews(Long id) {
        List<CommentEntity> comments = commentRepo.findByNewsId(id);
        List<CommentResponse> commentsResp = commentMapper.CommentsResponseList(comments);
        return commentsResp;
    }

    @Override
    public CommentCompleteResponse create(CommentRequest commentRequest) {
        CommentEntity commentEntity = this.commentMapper.toCommentEntity(commentRequest);
        CommentEntity commentSaved = this.commentRepo.save(commentEntity);
        return this.commentMapper.toCommentCompleteResponse(commentSaved);
    }

    @Override
    public CommentCompleteResponse update(Long id, CommentRequest commentRequest, String token) {
        var commentDB = commentRepo.findById(id).orElseThrow(() -> new OrgNotFoundException("Comment not found"));

        var tokenSplit = token.split(" ");
        var username = jwtUtils.extractUsername(tokenSplit[1]);
        var user = userDetails.loadUserByUsername(username);
        var role = user.getAuthorities().iterator().next().getAuthority();

        var userDB = userRepo.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (role.equalsIgnoreCase("ROLE_ADMIN") | commentDB.getUser().equals(userDB)) {
            this.commentMapper.changeValues(commentRequest, commentDB);
            return this.commentMapper.toCommentCompleteResponse(this.commentRepo.save(commentDB));
        } else {
            throw  new BadCredentialsException("Invalid Credentials");
        }
    }
}
