package com.leviis.realworldexample.article.application.readmodel;

import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.domain.User;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder(setterPrefix = "set")
public record ArticleWithAuthor(
        String slug,
        String title,
        String description,
        List<String> tags,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        boolean isFavorite,
        long favoriteCount,
        Author author) {
    public ArticleWithAuthor(
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

    public static List<ArticleWithAuthor> from(
            final List<Article> articles,
            final List<Tag> tags,
            final List<Long> favoriteArticleId,
            final Map<Long, Long> favoriteCount,
            final List<User> foundAuthors,
            final List<Long> foundIsFollowingAuthors) {
        final Map<Long, String> tagMap =
                tags.stream().collect(Collectors.toMap(Tag::id, Tag::name, (_, replacement) -> replacement));
        final Map<Long, Boolean> favoriteArticleMap = favoriteArticleId.stream()
                .collect(Collectors.toMap(id -> id, _ -> true, (_, replacement) -> replacement));
        final Map<Long, User> authorMap = foundAuthors.stream()
                .collect(Collectors.toMap(User::id, Function.identity(), (_, replacement) -> replacement));
        final Map<Long, Boolean> followingDataMap = foundIsFollowingAuthors.stream()
                .collect(Collectors.toMap(followingId -> followingId, _ -> true, (_, replacement) -> replacement));

        return articles.stream()
                .map(article -> builder()
                        .setSlug(getSlug(article.slug()))
                        .setTitle(article.title())
                        .setDescription(article.description())
                        .setTags(getTags(article.tagIds(), tagMap))
                        .setCreatedAt(article.createdAt())
                        .setUpdatedAt(article.updatedAt())
                        .setIsFavorite(favoriteArticleMap.getOrDefault(article.id(), false))
                        .setFavoriteCount(favoriteCount.getOrDefault(article.id(), 0L))
                        .setAuthor(Author.from(authorMap.get(article.authorId()), followingDataMap))
                        .build())
                .toList();
    }

    private static String getSlug(final Slug slug) {
        return String.format("%s-%s", slug.value(), slug.id());
    }

    private static List<String> getTags(final List<Long> tagId, final Map<Long, String> tagMap) {
        return tagId.stream().map(id -> tagMap.getOrDefault(id, null)).toList();
    }

    @Builder(setterPrefix = "set")
    public record Author(String username, String bio, String image, boolean isFollowing) {
        public static Author from(final User author, final Map<Long, Boolean> followingDataMap) {
            return builder()
                    .setUsername(author.username())
                    .setBio(author.bio())
                    .setImage(author.image())
                    .setIsFollowing(followingDataMap.getOrDefault(author.id(), false))
                    .build();
        }
    }
}
