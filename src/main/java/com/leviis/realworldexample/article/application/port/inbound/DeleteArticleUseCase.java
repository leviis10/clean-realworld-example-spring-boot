package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.command.DeleteArticleCommand;

@FunctionalInterface
public interface DeleteArticleUseCase {
    void execute(DeleteArticleCommand command);
}
