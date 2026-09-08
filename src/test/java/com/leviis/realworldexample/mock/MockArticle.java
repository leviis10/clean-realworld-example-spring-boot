package com.leviis.realworldexample.mock;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.articletag.ArticleTagEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.articletag.ArticleTagId;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class MockArticle {
    public static final Long ARTICLE_1_ID = 1L;
    public static final String ARTICLE_1_SLUG = "article-1";
    public static final UUID ARTICLE_1_SLUG_ID = UUID.randomUUID();
    public static final String ARTICLE_1_TITLE = "article 1";
    public static final String ARTICLE_1_DESCRIPTION = "description for article 1";
    public static final String ARTICLE_1_BODY = "body for article 1";
    public static final OffsetDateTime ARTICLE_1_CREATED_AT = OffsetDateTime.now();
    public static final OffsetDateTime ARTICLE_1_UPDATED_AT = OffsetDateTime.now();
    public static final Long ARTICLE_1_AUTHOR_ID = 1L;
    public static final List<Long> ARTICLE_1_TAG_IDS = List.of(1L, 2L);

    public static final Article ARTICLE_DOMAIN_1 = Article.builder()
            .setId(ARTICLE_1_ID)
            .setSlug(new Slug(ARTICLE_1_SLUG, ARTICLE_1_SLUG_ID))
            .setTitle(ARTICLE_1_TITLE)
            .setDescription(ARTICLE_1_DESCRIPTION)
            .setBody(ARTICLE_1_BODY)
            .setAuthorId(ARTICLE_1_AUTHOR_ID)
            .setTagIds(ARTICLE_1_TAG_IDS)
            .setCreatedAt(ARTICLE_1_CREATED_AT)
            .setUpdatedAt(ARTICLE_1_UPDATED_AT)
            .build();

    public static final ArticleEntity ARTICLE_ENTITY_1 = ArticleEntity.builder()
            .id(ARTICLE_1_ID)
            .slug(ARTICLE_1_SLUG)
            .slugId(ARTICLE_1_SLUG_ID)
            .title(ARTICLE_1_TITLE)
            .description(ARTICLE_1_DESCRIPTION)
            .body(ARTICLE_1_BODY)
            .author(MockAuthor.AUTHOR_ENTITY_MAP.get(ARTICLE_1_AUTHOR_ID))
            .createdAt(ARTICLE_1_CREATED_AT)
            .updatedAt(ARTICLE_1_UPDATED_AT)
            .tags(ARTICLE_1_TAG_IDS.stream()
                    .map(tagId -> ArticleTagEntity.builder()
                            .id(ArticleTagId.builder()
                                    .articleId(ARTICLE_1_ID)
                                    .tagId(tagId)
                                    .build())
                            .tag(MockTag.TAG_ENTITY_MAP.get(tagId))
                            .createdAt(OffsetDateTime.now())
                            .build())
                    .toList())
            .build();
}
