package com.leviis.realworldexample.article.adapter.outbound.persistence.article;

import org.springframework.data.jpa.domain.Specification;

public final class ArticleSpecification {
    private ArticleSpecification() {}

    public static Specification<ArticleEntity> from(final String tag, final String author, final String favoriteBy) {
        return Specification.where(hasAuthor(author)).and(hasTag(tag)).and(hasFavoriteBy(favoriteBy));
    }

    private static Specification<ArticleEntity> hasAuthor(final String author) {
        return (root, _, cb) ->
                author == null ? null : cb.like(root.join("author").get("username"), "%" + author + "%");
    }

    private static Specification<ArticleEntity> hasTag(final String tag) {
        return (root, _, cb) ->
                tag == null ? null : cb.like(root.join("tags").join("tag").get("name"), "%" + tag + "%");
    }

    private static Specification<ArticleEntity> hasFavoriteBy(final String favoriteBy) {
        return (root, _, cb) -> favoriteBy == null
                ? null
                : cb.like(root.join("favoritesBy").join("user").get("username"), "%" + favoriteBy + "%");
    }
}
