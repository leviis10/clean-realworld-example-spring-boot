package com.leviis.realworldexample.article.application.query;

import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.domain.User;
import java.time.OffsetDateTime;
import java.util.List;

public record ArticleWithBodyAndAuthor(
        String slug,
        String title,
        String description,
        String body,
        List<String> tags,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        boolean isFavorite,
        long favoritesCount,
        Author author) {
    public ArticleWithBodyAndAuthor(
            final String slug,
            final String title,
            final String description,
            final String body,
            final List<String> tags,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt,
            final boolean isFavorite,
            final long favoritesCount,
            final Author author) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = List.copyOf(tags);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isFavorite = isFavorite;
        this.favoritesCount = favoritesCount;
        this.author = author;
    }

    public record Author(String username, String bio, String image, boolean isFollowing) {
        public static AuthorBuilder builder() {
            return new AuthorBuilder();
        }

        public static Author from(final User author, final boolean isFollowingAuthor) {
            return builder()
                    .setUsername(author.username())
                    .setBio(author.bio())
                    .setImage(author.image())
                    .setIsFollowing(isFollowingAuthor)
                    .build();
        }

        public static final class AuthorBuilder {
            private String username;
            private String bio;
            private String image;
            private boolean isFollowing;

            public AuthorBuilder setUsername(final String username) {
                this.username = username;
                return this;
            }

            public AuthorBuilder setBio(final String bio) {
                this.bio = bio;
                return this;
            }

            public AuthorBuilder setImage(final String image) {
                this.image = image;
                return this;
            }

            public AuthorBuilder setIsFollowing(final boolean following) {
                isFollowing = following;
                return this;
            }

            public Author build() {
                return new Author(this.username, this.bio, this.image, this.isFollowing);
            }
        }
    }

    public static ArticleWithBodyAndAuthorBuilder builder() {
        return new ArticleWithBodyAndAuthorBuilder();
    }

    public static final class ArticleWithBodyAndAuthorBuilder {
        private String slug;
        private String title;
        private String description;
        private String body;
        private List<String> tags;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;
        private boolean isFavorite;
        private long favoritesCount;
        private Author author;

        public ArticleWithBodyAndAuthorBuilder setSlug(final String slug) {
            this.slug = slug;
            return this;
        }

        public ArticleWithBodyAndAuthorBuilder setTitle(final String title) {
            this.title = title;
            return this;
        }

        public ArticleWithBodyAndAuthorBuilder setDescription(final String description) {
            this.description = description;
            return this;
        }

        public ArticleWithBodyAndAuthorBuilder setBody(final String body) {
            this.body = body;
            return this;
        }

        public ArticleWithBodyAndAuthorBuilder setTags(final List<String> tags) {
            this.tags = List.copyOf(tags);
            return this;
        }

        public ArticleWithBodyAndAuthorBuilder setCreatedAt(final OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ArticleWithBodyAndAuthorBuilder setUpdatedAt(final OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ArticleWithBodyAndAuthorBuilder setIsFavorite(final boolean favorite) {
            isFavorite = favorite;
            return this;
        }

        public ArticleWithBodyAndAuthorBuilder setFavoritesCount(final long favoritesCount) {
            this.favoritesCount = favoritesCount;
            return this;
        }

        public ArticleWithBodyAndAuthorBuilder setAuthor(final Author author) {
            this.author = author;
            return this;
        }

        public ArticleWithBodyAndAuthor build() {
            return new ArticleWithBodyAndAuthor(
                    this.slug,
                    this.title,
                    this.description,
                    this.body,
                    this.tags,
                    this.createdAt,
                    this.updatedAt,
                    this.isFavorite,
                    this.favoritesCount,
                    this.author);
        }
    }

    public static ArticleWithBodyAndAuthor from(
            final Article article,
            final List<Tag> tags,
            final boolean isFavoriteArticle,
            final long favoritesCount,
            final User author,
            final boolean isFollowingAuthor) {
        return builder()
                .setSlug(getSlugFrom(article.slug()))
                .setTitle(article.title())
                .setDescription(article.description())
                .setBody(article.body())
                .setTags(getTagsFrom(tags))
                .setCreatedAt(article.createdAt())
                .setUpdatedAt(article.updatedAt())
                .setIsFavorite(isFavoriteArticle)
                .setFavoritesCount(favoritesCount)
                .setAuthor(Author.from(author, isFollowingAuthor))
                .build();
    }

    private static List<String> getTagsFrom(final List<Tag> tags) {
        return tags.stream().map(Tag::name).toList();
    }

    private static String getSlugFrom(final Slug slug) {
        return String.format("%s-%s", slug.value(), slug.id());
    }
}
