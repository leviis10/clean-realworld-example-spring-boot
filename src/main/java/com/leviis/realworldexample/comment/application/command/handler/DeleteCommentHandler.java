package com.leviis.realworldexample.comment.application.command.handler;

import com.leviis.realworldexample.comment.application.command.DeleteCommentCommand;
import com.leviis.realworldexample.comment.application.port.inbound.DeleteCommentUseCase;
import com.leviis.realworldexample.comment.application.port.outbound.CommentCommandRepository;
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
