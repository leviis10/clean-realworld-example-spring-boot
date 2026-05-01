package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.query.GetArticleQuery;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithBodyAndAuthor;

@FunctionalInterface
public interface GetArticleUseCase {
    ArticleWithBodyAndAuthor execute(GetArticleQuery query);
}
