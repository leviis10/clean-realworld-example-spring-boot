package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.command.CreateCommentCommand;
import com.leviis.realworldexample.article.application.readmodel.CommentWithAuthor;

@FunctionalInterface
public interface CreateCommentUseCase {
    CommentWithAuthor execute(CreateCommentCommand command);
}
