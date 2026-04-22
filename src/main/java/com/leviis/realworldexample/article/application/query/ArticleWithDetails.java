package com.leviis.realworldexample.article.application.query;

import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.domain.User;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record ArticleWithDetails(
        String slug,
        String title,
        String description,
        List<String> tags,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        boolean isFavorite,
        long favoriteCount,
        Author author) {
    public ArticleWithDetails(
            final String slug,
            final String title,
            final String description,
            final List<String> tags,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt,
            final boolean isFavorite,
            final long favoriteCount,
            final Author author) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.tags = List.copyOf(tags);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isFavorite = isFavorite;
        this.favoriteCount = favoriteCount;
        this.author = author;
    }

    public static List<ArticleWithDetails> from(
            final List<Article> articles,
            final List<Tag> tags,
            final List<Long> favoriteArticleId,
            final Map<Long, Long> favoriteCount,
            final List<User> foundAuthors,
            final User user,
            final List<Long> foundIsFollowingAuthors) {
        var tagMap = tags.stream().collect(Collectors.toMap(Tag::id, Tag::name, (_, replacement) -> replacement));
        var favoriteArticleMap = favoriteArticleId.stream()
                .collect(Collectors.toMap(id -> id, _ -> true, (_, replacement) -> replacement));
        var authorMap = foundAuthors.stream()
                .collect(Collectors.toMap(User::id, Function.identity(), (_, replacement) -> replacement));

        return articles.stream()
                .map(article -> builder()
                        .setSlug(getSlug(article.slug()))
                        .setTitle(article.title())
                        .setDescription(article.description())
                        .setTags(getTags(article.tagIds(), tagMap))
                        .setCreatedAt(article.createdAt())
                        .setUpdatedAt(article.updatedAt())
                        .setFavorite(favoriteArticleMap.getOrDefault(article.id(), false))
                        .setFavoriteCount(favoriteCount.getOrDefault(article.id(), 0L))
                        .setAuthor(Author.from(authorMap.get(article.authorId()), user, foundIsFollowingAuthors))
                        .build())
                .toList();
    }

    private static String getSlug(final Slug slug) {
        return String.format("%s-%s", slug.value(), slug.id());
    }

    private static List<String> getTags(final List<Long> tagId, final Map<Long, String> tagMap) {
        return tagId.stream().map(id -> tagMap.getOrDefault(id, null)).toList();
    }

    public record Author(String username, String bio, String image, boolean isFollowing) {
        public static Author from(final User author, final User user, final List<Long> followingData) {
            var followingDataMap = followingData.stream()
                    .collect(Collectors.toMap(followingId -> followingId, _ -> true, (_, replacement) -> replacement));

            return builder()
                    .setUsername(author.username())
                    .setBio(author.bio())
                    .setImage(author.image())
                    .setFollowing(user != null && followingDataMap.getOrDefault(author.id(), false))
                    .build();
        }

        public static AuthorBuilder builder() {
            return new AuthorBuilder();
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

            public AuthorBuilder setFollowing(final boolean following) {
                isFollowing = following;
                return this;
            }

            public Author build() {
                return new Author(this.username, this.bio, this.image, this.isFollowing);
            }
        }
    }

    public static ArticleWithDetailsBuilder builder() {
        return new ArticleWithDetailsBuilder();
    }

    public static final class ArticleWithDetailsBuilder {
        private String slug;
        private String title;
        private String description;
        private List<String> tags;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;
        private boolean isFavorite;
        private long favoriteCount;
        private Author author;

        public ArticleWithDetailsBuilder setSlug(final String slug) {
            this.slug = slug;
            return this;
        }

        public ArticleWithDetailsBuilder setTitle(final String title) {
            this.title = title;
            return this;
        }

        public ArticleWithDetailsBuilder setDescription(final String description) {
            this.description = description;
            return this;
        }

        public ArticleWithDetailsBuilder setTags(final List<String> tags) {
            this.tags = List.copyOf(tags);
            return this;
        }

        public ArticleWithDetailsBuilder setCreatedAt(final OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ArticleWithDetailsBuilder setUpdatedAt(final OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ArticleWithDetailsBuilder setFavorite(final boolean favorite) {
            isFavorite = favorite;
            return this;
        }

        public ArticleWithDetailsBuilder setFavoriteCount(final long favoriteCount) {
            this.favoriteCount = favoriteCount;
            return this;
        }

        public ArticleWithDetailsBuilder setAuthor(final Author author) {
            this.author = author;
            return this;
        }

        public ArticleWithDetails build() {
            return new ArticleWithDetails(
                    this.slug,
                    this.title,
                    this.description,
                    this.tags,
                    this.createdAt,
                    this.updatedAt,
                    this.isFavorite,
                    this.favoriteCount,
                    this.author);
        }
    }
}
