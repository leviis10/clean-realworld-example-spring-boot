package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.command.UnfavoriteArticleCommand;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithBodyAndAuthor;

@FunctionalInterface
public interface UnfavoriteArticleUseCase {
    ArticleWithBodyAndAuthor execute(UnfavoriteArticleCommand command);
}
