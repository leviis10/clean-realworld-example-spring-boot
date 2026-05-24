package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.command.DeleteCommentCommand;

@FunctionalInterface
public interface DeleteCommentUseCase {
    void execute(DeleteCommentCommand command);
}
