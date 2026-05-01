package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.command.CreateArticleCommand;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithBodyAndAuthor;

@FunctionalInterface
public interface CreateArticleUseCase {
    ArticleWithBodyAndAuthor execute(CreateArticleCommand command);
}
