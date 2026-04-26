package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.query.ArticleWithDetails;
import com.leviis.realworldexample.article.application.query.FindAllFeedArticleQuery;
import java.util.List;

public interface FindAllFeedArticleUseCase {
    List<ArticleWithDetails> execute(FindAllFeedArticleQuery query);
}
