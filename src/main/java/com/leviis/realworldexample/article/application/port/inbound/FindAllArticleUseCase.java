package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.query.FindAllArticleQuery;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithAuthor;
import java.util.List;

@FunctionalInterface
public interface FindAllArticleUseCase {
    List<ArticleWithAuthor> execute(FindAllArticleQuery query);
}
