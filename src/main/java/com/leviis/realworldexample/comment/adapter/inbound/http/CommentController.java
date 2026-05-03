package com.leviis.realworldexample.comment.adapter.inbound.http;

import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.comment.adapter.inbound.http.dto.request.CreateCommentRequest;
import com.leviis.realworldexample.comment.adapter.inbound.http.dto.response.CreateCommentResponse;
import com.leviis.realworldexample.comment.application.command.CreateCommentCommand;
import com.leviis.realworldexample.comment.application.port.inbound.CreateCommentUseCase;
import com.leviis.realworldexample.comment.application.readmodel.CommentWithAuthor;
import com.leviis.realworldexample.comment.domain.Comment;
import com.leviis.realworldexample.infrastructure.UserContext;
import com.leviis.realworldexample.utils.SlugUtils;
import com.leviis.realworldexample.utils.http.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comments")
public final class CommentController {
    private final CreateCommentUseCase createCommentUseCase;

    @PostMapping
    public ResponseEntity<ResponseWrapper<CreateCommentResponse>> create(
            @AuthenticationPrincipal final UserContext userContext,
            @Valid @RequestBody final CreateCommentRequest request) {
        final CommentWithAuthor createComment = createCommentUseCase.execute(CreateCommentCommand.builder()
                .comment(request.into(Comment.class))
                .slug(new Slug(SlugUtils.getTitleFrom(request.getSlug()), SlugUtils.getIdFrom(request.getSlug())))
                .author(userContext.intoUserDomain())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(
                        "Successfully created new comment", CreateCommentResponse.from(createComment)));
    }
}
