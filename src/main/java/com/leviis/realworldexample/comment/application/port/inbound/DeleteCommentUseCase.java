package com.leviis.realworldexample.comment.application.port.inbound;

import com.leviis.realworldexample.comment.application.command.DeleteCommentCommand;

@FunctionalInterface
public interface DeleteCommentUseCase {
    void execute(DeleteCommentCommand command);
}
