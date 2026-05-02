package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.command.UpdateArticleCommand;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithBodyAndAuthor;

@FunctionalInterface
public interface UpdateArticleUseCase {
    ArticleWithBodyAndAuthor execute(UpdateArticleCommand command);
}
