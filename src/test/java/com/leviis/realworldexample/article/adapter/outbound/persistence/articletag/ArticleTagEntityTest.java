package com.leviis.realworldexample.article.adapter.outbound.persistence.articletag;

import static org.junit.jupiter.api.Assertions.*;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.tag.domain.Tag;
import org.junit.jupiter.api.Test;

class ArticleTagEntityTest {
    @Test
    public void from_positiveCase_returnArticleTagEntity() {
        Long articleId = 1L;
        ArticleEntity articleEntity = ArticleEntity.builder().id(articleId).build();

        Long tagId = 1L;
        Tag tag = Tag.builder().setId(tagId).build();
        ArticleTagEntity response = ArticleTagEntity.from(articleEntity, tag);

        assertEquals(articleId, response.getId().getArticleId());
        assertEquals(tagId, response.getId().getTagId());
        assertEquals(articleEntity, response.getArticle());
        assertEquals(tagId, response.getTag().getId());
    }

    @Test
    public void from_articleEntityAndTagIsNull_throwIllegalArgumentException() {
        ArticleEntity articleEntity = null;
        Tag tag = null;
        assertThrows(IllegalArgumentException.class, () -> ArticleTagEntity.from(articleEntity, tag));
    }

    @Test
    public void from_tagIsNull_returnArticleTagEntity() {
        Long articleId = 1L;
        ArticleEntity articleEntity = ArticleEntity.builder().id(articleId).build();

        Tag tag = null;
        ArticleTagEntity response = ArticleTagEntity.from(articleEntity, tag);

        assertEquals(articleId, response.getId().getArticleId());
        assertNull(response.getId().getTagId());
        assertEquals(articleEntity, response.getArticle());
        assertNull(response.getTag().getId());
    }

    @Test
    public void from_articleEntityIsNull_returnArticleTagEntity() {
        ArticleEntity articleEntity = null;

        Long tagId = 1L;
        Tag tag = Tag.builder().setId(tagId).build();
        ArticleTagEntity response = ArticleTagEntity.from(articleEntity, tag);

        assertNull(response.getId().getArticleId());
        assertEquals(tagId, response.getId().getTagId());
        assertNull(response.getArticle());
        assertEquals(tagId, response.getTag().getId());
    }
}
