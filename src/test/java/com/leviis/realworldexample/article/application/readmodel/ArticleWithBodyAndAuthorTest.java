package com.leviis.realworldexample.article.application.readmodel;

import static org.junit.jupiter.api.Assertions.*;

import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArticleWithBodyAndAuthorTest {
    @Nested
    class From6Parameters {
        @Test
        public void from_positiveCase_returnArticleWithBodyAndAuthor() {
            Tag tag1 = Tag.builder().setId(1L).setName("tag1").build();
            Tag tag2 = Tag.builder().setId(2L).setName("tag2").build();
            Article article = Article.builder()
                    .setSlug(new Slug("article", UUID.randomUUID()))
                    .setTitle("article title")
                    .setDescription("article description")
                    .setBody("article body")
                    .setTagIds(List.of(tag1.id(), tag2.id()))
                    .build();
            List<Tag> tags = List.of(tag1, tag2);
            boolean isFavoriteArticle = true;
            long favoritesCount = 10L;
            User author = User.builder()
                    .setUsername("author")
                    .setEmail(new Email("author@example.com"))
                    .setBio("author bio")
                    .setImage("author image")
                    .build();
            boolean isFollowingAuthor = true;
            ArticleWithBodyAndAuthor response = ArticleWithBodyAndAuthor.from(
                    article, tags, isFavoriteArticle, favoritesCount, author, isFollowingAuthor);

            assertEquals(article.slug().toString(), response.slug());
            assertEquals(article.title(), response.title());
            assertEquals(article.description(), response.description());
            assertEquals(article.body(), response.body());
            assertEquals(2, response.tags().size());
            assertEquals(tag1.name(), response.tags().getFirst());
            assertEquals(tag2.name(), response.tags().getLast());
            assertTrue(response.isFavorite());
            assertEquals(favoritesCount, response.favoritesCount());
            assertEquals(author.username(), response.author().username());
            assertEquals(author.bio(), response.author().bio());
            assertEquals(author.image(), response.author().image());
            assertTrue(response.author().isFollowing());
        }
    }
}
