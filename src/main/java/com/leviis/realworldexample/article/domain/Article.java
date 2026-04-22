package com.leviis.realworldexample.article.domain;

import java.time.OffsetDateTime;
import java.util.List;

public record Article(
        Long id,
        Slug slug,
        String title,
        String description,
        String body,
        Long authorId,
        List<Long> tagIds,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
    public Article(
            final Long id,
            final Slug slug,
            final String title,
            final String description,
            final String body,
            final Long authorId,
            final List<Long> tagIds,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.authorId = authorId;
        this.tagIds = List.copyOf(tagIds);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ArticleBuilder builder() {
        return new ArticleBuilder();
    }

    public static final class ArticleBuilder {
        private Long id;
        private Slug slug;
        private String title;
        private String description;
        private String body;
        private Long authorId;
        private List<Long> tagIds;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public ArticleBuilder setId(final Long id) {
            this.id = id;
            return this;
        }

        public ArticleBuilder setSlug(final Slug slug) {
            this.slug = slug;
            return this;
        }

        public ArticleBuilder setTitle(final String title) {
            this.title = title;
            return this;
        }

        public ArticleBuilder setDescription(final String description) {
            this.description = description;
            return this;
        }

        public ArticleBuilder setBody(final String body) {
            this.body = body;
            return this;
        }

        public ArticleBuilder setAuthorId(final Long authorId) {
            this.authorId = authorId;
            return this;
        }

        public ArticleBuilder setTagIds(final List<Long> tagIds) {
            this.tagIds = List.copyOf(tagIds);
            return this;
        }

        public ArticleBuilder setCreatedAt(final OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ArticleBuilder setUpdatedAt(final OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Article build() {
            return new Article(
                    this.id,
                    this.slug,
                    this.title,
                    this.description,
                    this.body,
                    this.authorId,
                    this.tagIds,
                    this.createdAt,
                    this.updatedAt);
        }
    }
}
