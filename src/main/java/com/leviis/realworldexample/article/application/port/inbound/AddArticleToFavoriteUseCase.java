package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.command.AddArticleToFavoriteCommand;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithBodyAndAuthor;

@FunctionalInterface
public interface AddArticleToFavoriteUseCase {
    ArticleWithBodyAndAuthor execute(AddArticleToFavoriteCommand command);
}
