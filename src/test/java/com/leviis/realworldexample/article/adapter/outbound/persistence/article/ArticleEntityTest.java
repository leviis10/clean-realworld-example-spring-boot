package com.leviis.realworldexample.article.adapter.outbound.persistence.article;

import static org.junit.jupiter.api.Assertions.*;

import com.leviis.realworldexample.article.adapter.outbound.persistence.articletag.ArticleTagEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.articletag.ArticleTagId;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArticleEntityTest {
    @Nested
    class From {
        @Nested
        class ArticleAndTagMap {
            @Test
            public void from_positiveCase_returnArticleEntity() {
                Article article = Article.builder()
                        .setId(1L)
                        .setSlug(new Slug("test-slug", UUID.randomUUID()))
                        .setTitle("test-title")
                        .setDescription("test-description")
                        .setBody("test-body")
                        .setAuthorId(1L)
                        .setCreatedAt(OffsetDateTime.now())
                        .setUpdatedAt(OffsetDateTime.now())
                        .setTagIds(List.of(1L, 3L))
                        .build();
                String articleTag1 = "test-tag-1";
                String articleTag2 = "test-tag-2";
                String articleTag3 = "test-tag-3";
                Map<Long, Tag> tagMap = Map.of(
                        1L,
                        Tag.builder().setId(1L).setName(articleTag1).build(),
                        2L,
                        Tag.builder().setId(2L).setName(articleTag2).build(),
                        3L,
                        Tag.builder().setId(3L).setName(articleTag3).build());
                ArticleEntity response = ArticleEntity.from(article, tagMap);

                assertEquals(article.id(), response.getId());
                assertEquals(article.slug().value(), response.getSlug());
                assertEquals(article.slug().id(), response.getSlugId());
                assertEquals(article.title(), response.getTitle());
                assertEquals(article.description(), response.getDescription());
                assertEquals(article.body(), response.getBody());
                assertEquals(article.authorId(), response.getAuthor().getId());
                assertEquals(article.createdAt(), response.getCreatedAt());
                assertEquals(article.updatedAt(), response.getUpdatedAt());
                assertTrue(response.getTags().stream()
                        .anyMatch(tag -> tag.getTag().getName().equals(articleTag1)));
                assertTrue(response.getTags().stream()
                        .anyMatch(tag -> tag.getTag().getName().equals(articleTag3)));
                assertFalse(response.getTags().stream()
                        .anyMatch(tag -> tag.getTag().getName().equals(articleTag2)));
            }

            @Test
            public void from_articleIsNull_throwNullPointerException() {
                Article article = null;
                String articleTag1 = "test-tag-1";
                String articleTag2 = "test-tag-2";
                String articleTag3 = "test-tag-3";
                Map<Long, Tag> tagMap = Map.of(
                        1L,
                        Tag.builder().setId(1L).setName(articleTag1).build(),
                        2L,
                        Tag.builder().setId(2L).setName(articleTag2).build(),
                        3L,
                        Tag.builder().setId(3L).setName(articleTag3).build());
                assertThrows(NullPointerException.class, () -> ArticleEntity.from(article, tagMap));
            }
        }

        @Nested
        class ArticleId {}
    }

    @Nested
    class Into {
        @Test
        public void into_article_returnArticle() {
            long articleId = 1L;
            String articleSlug = "test-article-slug";
            UUID articleSlugId = UUID.randomUUID();
            String articleTitle = "test-article-title";
            String articleDescription = "test-article-description";
            String articleBody = "test-article-body";
            UserEntity articleAuthor = UserEntity.builder().id(1L).build();
            ArticleTagEntity articleTagEntity1 = ArticleTagEntity.builder()
                    .id(ArticleTagId.builder().tagId(1L).build())
                    .build();
            ArticleTagEntity articleTagEntity2 = ArticleTagEntity.builder()
                    .id(ArticleTagId.builder().tagId(2L).build())
                    .build();
            ArticleTagEntity articleTagEntity3 = ArticleTagEntity.builder()
                    .id(ArticleTagId.builder().tagId(3L).build())
                    .build();
            List<ArticleTagEntity> articleTags = List.of(articleTagEntity1, articleTagEntity2, articleTagEntity3);
            OffsetDateTime articleCreatedAt = OffsetDateTime.now();
            OffsetDateTime articleUpdatedAt = OffsetDateTime.now();

            ArticleEntity articleEntity = ArticleEntity.builder()
                    .id(articleId)
                    .slug(articleSlug)
                    .slugId(articleSlugId)
                    .title(articleTitle)
                    .description(articleDescription)
                    .body(articleBody)
                    .author(articleAuthor)
                    .tags(articleTags)
                    .createdAt(articleCreatedAt)
                    .updatedAt(articleUpdatedAt)
                    .build();
            Article response = articleEntity.into(Article.class);

            assertEquals(articleId, response.id());
            assertEquals(articleSlug, response.slug().value());
            assertEquals(articleSlugId, response.slug().id());
            assertEquals(articleTitle, response.title());
            assertEquals(articleDescription, response.description());
            assertEquals(articleBody, response.body());
            assertEquals(articleAuthor.getId(), response.authorId());
            assertEquals(
                    articleTags.stream()
                            .map(articleTag -> articleTag.getId().getTagId())
                            .toList(),
                    response.tagIds());
            assertEquals(articleCreatedAt, response.createdAt());
            assertEquals(articleUpdatedAt, response.updatedAt());
        }
    }
}
