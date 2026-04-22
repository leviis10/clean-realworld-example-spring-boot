package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.query.ArticleWithDetails;
import com.leviis.realworldexample.article.application.query.FindAllArticleQuery;
import java.util.List;

public interface FindAllArticleUseCase {
    List<ArticleWithDetails> execute(FindAllArticleQuery query);
}
