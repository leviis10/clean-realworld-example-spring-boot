package com.leviis.realworldexample.article.application.readmodel;

import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.domain.User;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Builder;

@Builder(setterPrefix = "set")
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

    @Builder(setterPrefix = "set")
    public record Author(String username, String bio, String image, boolean isFollowing) {
        public static Author from(final User author, final boolean isFollowingAuthor) {
            return builder()
                    .setUsername(author.username())
                    .setBio(author.bio())
                    .setImage(author.image())
                    .setIsFollowing(isFollowingAuthor)
                    .build();
        }
    }

    public static ArticleWithBodyAndAuthor from(
            final User author,
            final List<Tag> tags,
            final boolean isFavorite,
            final long favoritesCount,
            final Article article) {
        return from(article, tags, isFavorite, favoritesCount, author, false);
    }

    public static ArticleWithBodyAndAuthor from(final User author, final List<Tag> tags, final Article article) {
        return from(article, tags, false, 0, author, false);
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
