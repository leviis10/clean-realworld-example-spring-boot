package com.leviis.realworldexample.article.application.command.handler;

import com.leviis.realworldexample.article.application.command.DeleteCommentCommand;
import com.leviis.realworldexample.article.application.port.inbound.DeleteCommentUseCase;
import com.leviis.realworldexample.article.application.port.outbound.CommentCommandRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteCommentHandler implements DeleteCommentUseCase {
    private final CommentCommandRepository commentCommandRepository;

    @Override
    public void execute(final DeleteCommentCommand command) {
        commentCommandRepository.deleteByIdAndArticleSlug(
                command.authenticatedUserId(), command.commentId(), command.articleSlug());
    }
}
