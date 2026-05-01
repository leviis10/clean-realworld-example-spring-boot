package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.query.FindAllFeedArticleQuery;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithAuthor;
import java.util.List;

@FunctionalInterface
public interface FindAllFeedArticleUseCase {
    List<ArticleWithAuthor> execute(FindAllFeedArticleQuery query);
}
