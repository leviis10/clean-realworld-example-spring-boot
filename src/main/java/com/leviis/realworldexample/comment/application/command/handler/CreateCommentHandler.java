package com.leviis.realworldexample.comment.application.command.handler;

import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.comment.application.command.CreateCommentCommand;
import com.leviis.realworldexample.comment.application.port.inbound.CreateCommentUseCase;
import com.leviis.realworldexample.comment.application.port.outbound.CommentCommandRepository;
import com.leviis.realworldexample.comment.application.readmodel.CommentWithAuthor;
import com.leviis.realworldexample.comment.domain.Comment;
import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;

public final class CreateCommentHandler implements CreateCommentUseCase {
    private final CommentCommandRepository commentCommandRepository;
    private final ArticleQueryRepository articleQueryRepository;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Repository interfaces are effectively immutable - no internal state is exposed")
    public CreateCommentHandler(
            final CommentCommandRepository commentCommandRepository,
            final ArticleQueryRepository articleQueryRepository) {
        this.commentCommandRepository = commentCommandRepository;
        this.articleQueryRepository = articleQueryRepository;
    }

    @Override
    public CommentWithAuthor execute(final CreateCommentCommand command) {
        final Article foundArticle = articleQueryRepository
                .getBySlug(command.slug())
                .orElseThrow(() -> new RuntimeException("Article not found"));
        final Comment newComment = command.comment().toBuilder()
                .setArticleId(foundArticle.id())
                .setAuthorId(command.author().id())
                .build();
        final Comment createdComment = commentCommandRepository.create(newComment);
        return CommentWithAuthor.from(createdComment, command.author());
    }
}
