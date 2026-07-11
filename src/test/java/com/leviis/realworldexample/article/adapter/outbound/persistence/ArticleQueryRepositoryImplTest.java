package com.leviis.realworldexample.article.adapter.outbound.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.article.JpaArticleRepository;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class ArticleQueryRepositoryImplTest {
    @Mock
    private JpaArticleRepository jpaArticleRepository;

    @InjectMocks
    private ArticleQueryRepositoryImpl articleQueryRepository;

    @Nested
    class FindAll {
        @Test
        public void findAll_positiveCase_returnListOfArticle() {
            ArticleEntity articleEntity1 = ArticleEntity.builder()
                    .id(1L)
                    .tags(List.of())
                    .author(UserEntity.builder().build())
                    .title("test-title")
                    .build();
            ArticleEntity articleEntity2 = ArticleEntity.builder()
                    .id(2L)
                    .tags(List.of())
                    .author(UserEntity.builder().build())
                    .title("test-title")
                    .build();
            ArticleEntity articleEntity3 = ArticleEntity.builder()
                    .id(3L)
                    .tags(List.of())
                    .author(UserEntity.builder().build())
                    .title("test-title")
                    .build();
            List<ArticleEntity> content = List.of(articleEntity1, articleEntity2, articleEntity3);
            int searchLimit = 10;
            int searchOffset = 0;
            when(jpaArticleRepository.findAll(any(Specification.class), any(PageRequest.class)))
                    .thenReturn(new PageImpl<>(content, PageRequest.of(searchOffset, searchLimit), content.size()));

            String searchTag = "test-search-tag";
            String searchAuthor = "test-search-author";
            String searchFavoriteBy = "test-search-favoriteBy";
            List<Article> response = articleQueryRepository.findAll(
                    searchTag, searchAuthor, searchFavoriteBy, searchLimit, searchOffset);

            assertEquals(content.size(), response.size());
            for (int i = 0; i < response.size(); i++) {
                assertEquals(content.get(i).getId(), response.get(i).id());
            }
        }
    }
}
