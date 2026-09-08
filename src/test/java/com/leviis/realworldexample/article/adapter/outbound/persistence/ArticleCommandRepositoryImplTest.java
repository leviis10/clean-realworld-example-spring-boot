package com.leviis.realworldexample.article.adapter.outbound.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.article.JpaArticleRepository;
import com.leviis.realworldexample.article.adapter.outbound.persistence.articletag.JpaArticleTagRepository;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.mock.MockArticle;
import com.leviis.realworldexample.mock.MockTag;
import com.leviis.realworldexample.tag.domain.Tag;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleCommandRepositoryImplTest {
    @Mock
    private JpaArticleRepository jpaArticleRepository;

    @Mock
    private JpaArticleTagRepository jpaArticleTagRepository;

    @InjectMocks
    private ArticleCommandRepositoryImpl articleCommandRepository;

    @Test
    public void create_positiveCase_returnArticleDomain() {
        when(jpaArticleRepository.save(any(ArticleEntity.class))).thenReturn(MockArticle.ARTICLE_ENTITY_1);
        when(jpaArticleTagRepository.saveAll(anyList())).thenReturn(anyList());

        Article article = MockArticle.ARTICLE_DOMAIN_1;
        Map<Long, Tag> tagMap = MockTag.TAG_MAP;
        Article response = articleCommandRepository.create(article, tagMap);

        assertEquals(article.id(), response.id());
        assertEquals(article.slug().value(), response.slug().value());
        assertEquals(article.slug().id(), response.slug().id());
        assertEquals(article.title(), response.title());
        assertEquals(article.description(), response.description());
        assertEquals(article.body(), response.body());
        assertEquals(article.authorId(), response.authorId());
        assertEquals(article.tagIds().size(), response.tagIds().size());
        for (int i = 0; i < article.tagIds().size(); i++) {
            assertEquals(article.tagIds().get(i), response.tagIds().get(i));
        }
        assertEquals(article.createdAt(), response.createdAt());
        assertEquals(article.updatedAt(), response.updatedAt());
    }
}
