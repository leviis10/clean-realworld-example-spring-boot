package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.query.ArticleWithAuthor;
import com.leviis.realworldexample.article.application.query.FindAllArticleQuery;
import java.util.List;

public interface FindAllArticleUseCase {
    List<ArticleWithAuthor> execute(FindAllArticleQuery query);
}
