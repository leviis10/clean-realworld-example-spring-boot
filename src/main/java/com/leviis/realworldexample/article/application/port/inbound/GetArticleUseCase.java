package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.query.ArticleWithBodyAndAuthor;
import com.leviis.realworldexample.article.application.query.GetArticleQuery;

public interface GetArticleUseCase {
    ArticleWithBodyAndAuthor execute(GetArticleQuery query);
}
