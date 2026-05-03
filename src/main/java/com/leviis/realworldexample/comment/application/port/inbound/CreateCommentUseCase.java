package com.leviis.realworldexample.comment.application.port.inbound;

import com.leviis.realworldexample.comment.application.command.CreateCommentCommand;
import com.leviis.realworldexample.comment.application.readmodel.CommentWithAuthor;

@FunctionalInterface
public interface CreateCommentUseCase {
    CommentWithAuthor execute(CreateCommentCommand command);
}
