package com.leviis.realworldexample.article.application.port.outbound;

import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;

@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface UserFavoriteArticleCommandRepository {
    Article create(long authenticatedUserId, Slug articleSlug);
}
