package com.leviis.realworldexample.article.application.readmodel;

import static org.junit.jupiter.api.Assertions.*;

import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArticleWithAuthorTest {
    @Test
    public void from_positiveCase_returnListOfArticleWithAuthor() {
        Tag tag1 = Tag.builder().setId(1L).setName("tag1").build();
        Tag tag2 = Tag.builder().setId(2L).setName("tag2").build();
        Tag tag3 = Tag.builder().setId(3L).setName("tag3").build();
        User author1 = User.builder()
                .setId(1L)
                .setEmail(new Email("author1@example.com"))
                .setUsername("author1")
                .build();
        User author2 = User.builder()
                .setId(2L)
                .setEmail(new Email("author2@example.com"))
                .setUsername("author2")
                .build();
        User author3 = User.builder()
                .setId(3L)
                .setEmail(new Email("author3@example.com"))
                .setUsername("author3")
                .build();
        String article1Title = "article title 1";
        String article2Title = "article title 2";
        Slug article1Slug = new Slug("article-slug-1", UUID.randomUUID());
        Slug article2Slug = new Slug("article-slug-2", UUID.randomUUID());
        Article article1 = Article.builder()
                .setId(1L)
                .setTitle(article1Title)
                .setTagIds(List.of(tag1.id(), tag2.id()))
                .setAuthorId(author1.id())
                .setSlug(article1Slug)
                .build();
        Article article2 = Article.builder()
                .setId(2L)
                .setTitle(article2Title)
                .setTagIds(List.of(tag2.id(), tag3.id()))
                .setAuthorId(author2.id())
                .setSlug(article2Slug)
                .build();
        List<Article> articles = List.of(article1, article2);
        List<Tag> tags = List.of(tag1, tag2, tag3);
        List<Long> favoriteArticleId = List.of(1L, 3L);
        Map<Long, Long> favoriteCount = Map.of(
                1L, 10L,
                2L, 20L);
        List<User> foundAuthors = List.of(author1, author2);
        List<@Nullable Long> foundIsFollowingAuthors = List.of(author1.id(), author3.id());
        List<ArticleWithAuthor> response = ArticleWithAuthor.from(
                articles, tags, favoriteArticleId, favoriteCount, foundAuthors, foundIsFollowingAuthors);

        assertEquals(2, response.size());

        assertEquals(article1Slug.toString(), response.getFirst().slug());
        assertEquals(article1Title, response.getFirst().title());
        assertEquals(2, response.getFirst().tags().size());
        assertEquals(tag1.name(), response.getFirst().tags().getFirst());
        assertEquals(tag2.name(), response.getFirst().tags().get(1));
        assertTrue(response.getFirst().isFavorite());
        assertEquals(favoriteCount.get(article1.id()), response.getFirst().favoriteCount());

        assertEquals(article2Slug.toString(), response.get(1).slug());
        assertEquals(article2Title, response.get(1).title());
        assertEquals(2, response.get(1).tags().size());
        assertEquals(tag2.name(), response.get(1).tags().getFirst());
        assertEquals(tag3.name(), response.get(1).tags().get(1));
        assertFalse(response.get(1).isFavorite());
        assertEquals(favoriteCount.get(article2.id()), response.get(1).favoriteCount());
    }

    @Nested
    class AuthorTest {
        @Test
        public void from_positiveCase_returnAuthorWithIsFollowingTrue() {
            User author = User.builder()
                    .setId(1L)
                    .setEmail(new Email("author@example.com"))
                    .setUsername("author")
                    .setBio("bio")
                    .setImage("image")
                    .build();
            Map<Long, Boolean> followingDataMap = Map.of(
                    1L, true,
                    2L, true);
            ArticleWithAuthor.Author response = ArticleWithAuthor.Author.from(author, followingDataMap);

            assertEquals(author.username(), response.username());
            assertEquals(author.bio(), response.bio());
            assertEquals(author.image(), response.image());
            assertTrue(response.isFollowing());
        }

        @Test
        public void from_authorIsNotInFollowingMap_returnAuthorWithIsFollowingFalse() {
            User author = User.builder()
                    .setId(3L)
                    .setEmail(new Email("author@example.com"))
                    .setUsername("author")
                    .setBio("bio")
                    .setImage("image")
                    .build();
            Map<Long, Boolean> followingDataMap = Map.of(
                    1L, true,
                    2L, true);
            ArticleWithAuthor.Author response = ArticleWithAuthor.Author.from(author, followingDataMap);

            assertEquals(author.username(), response.username());
            assertEquals(author.bio(), response.bio());
            assertEquals(author.image(), response.image());
            assertFalse(response.isFollowing());
        }
    }
}
