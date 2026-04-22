package com.leviis.realworldexample.article.application.port.outbound;

import com.leviis.realworldexample.article.domain.Article;
import java.util.List;

public interface ArticleQueryRepository {
    List<Article> findAll(String tag, String author, String favoriteBy, int limit, int offset);
}
