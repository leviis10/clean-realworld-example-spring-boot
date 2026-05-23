package com.leviis.realworldexample.comment.adapter.inbound.http;

import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.comment.adapter.inbound.http.dto.queryparameter.FindAllArticleBySlugQueryParameter;
import com.leviis.realworldexample.comment.adapter.inbound.http.dto.request.CreateCommentRequest;
import com.leviis.realworldexample.comment.adapter.inbound.http.dto.request.DeleteByIdAndArticleSlugRequest;
import com.leviis.realworldexample.comment.adapter.inbound.http.dto.response.CreateCommentResponse;
import com.leviis.realworldexample.comment.adapter.inbound.http.dto.response.FindAllByArticleSlugResponse;
import com.leviis.realworldexample.comment.application.command.CreateCommentCommand;
import com.leviis.realworldexample.comment.application.command.DeleteCommentCommand;
import com.leviis.realworldexample.comment.application.port.inbound.CreateCommentUseCase;
import com.leviis.realworldexample.comment.application.port.inbound.DeleteCommentUseCase;
import com.leviis.realworldexample.comment.application.port.inbound.FindAllCommentUseCase;
import com.leviis.realworldexample.comment.application.query.FindAllCommentQuery;
import com.leviis.realworldexample.comment.application.readmodel.CommentWithAuthor;
import com.leviis.realworldexample.comment.domain.Comment;
import com.leviis.realworldexample.infrastructure.UserContext;
import com.leviis.realworldexample.user.domain.User;
import com.leviis.realworldexample.utils.SlugUtils;
import com.leviis.realworldexample.utils.http.ResponseWrapper;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comments")
public final class CommentController {
    private final CreateCommentUseCase createCommentUseCase;
    private final FindAllCommentUseCase findAllCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;

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

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<FindAllByArticleSlugResponse>>> findAllByArticleSlug(
            @Nullable @AuthenticationPrincipal final UserContext userContext,
            @Valid @ModelAttribute final FindAllArticleBySlugQueryParameter queryParameter) {
        final User authenticatedUser = Optional.ofNullable(userContext)
                .map(UserContext::intoUserDomain)
                .orElse(null);
        final Slug articleSlug = new Slug(
                SlugUtils.getTitleFrom(queryParameter.articleSlug()),
                SlugUtils.getIdFrom(queryParameter.articleSlug()));
        final List<CommentWithAuthor> foundComments = findAllCommentUseCase.execute(FindAllCommentQuery.builder()
                .setUser(authenticatedUser)
                .setArticleSlug(articleSlug)
                .build());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        "Successfully fetch all comments", FindAllByArticleSlugResponse.from(foundComments)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByIdAndArticleSlug(
            @AuthenticationPrincipal final UserContext userContext,
            @PathVariable("id") final Long commentId,
            @Valid @RequestBody final DeleteByIdAndArticleSlugRequest request) {
        deleteCommentUseCase.execute(DeleteCommentCommand.builder()
                .setAuthenticatedUserId(userContext.getId())
                .setCommentId(commentId)
                .setArticleSlug(
                        new Slug(SlugUtils.getTitleFrom(request.getSlug()), SlugUtils.getIdFrom(request.getSlug())))
                .build());

        return ResponseEntity.noContent().build();
    }
}
