package com.leviis.realworldexample.article.application.port.outbound;

import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;

public interface UserFavoriteArticleCommandRepository {
    Article create(long authenticatedUserId, Slug articleSlug);

    void delete(long authenticatedUserId, long articleId);
}
