package com.leviis.realworldexample.article.application.port.outbound;

import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import java.util.List;
import java.util.Optional;

public interface ArticleQueryRepository {
    List<Article> findAll(String tag, String author, String favoriteBy, int limit, int offset);

    List<Article> findAllByAuthorIdIn(List<Long> authorIds, int offset, int limit);

    Optional<Article> getBySlug(Slug slug);
}
