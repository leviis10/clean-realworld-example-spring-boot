package com.leviis.realworldexample.article.adapter.outbound.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle.JpaUserFavoriteArticleRepository;
import com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle.UserFavoriteArticleEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle.UserFavoriteArticleId;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserFavoriteArticleQueryRepositoryImplTest {
    @Mock
    private JpaUserFavoriteArticleRepository jpaUserFavoriteArticleRepository;

    @InjectMocks
    private UserFavoriteArticleQueryRepositoryImpl userFavoriteArticleQueryRepository;

    @Nested
    class FindUserArticleFavoriteIn {
        @Test
        public void findUserArticleFavoriteIn_positiveCase_returnListOfArticleId() {
            List<Long> returnedArticleId = List.of(1L, 2L, 3L);
            when(jpaUserFavoriteArticleRepository.findByUserAndArticleIn(any(UserEntity.class), any(List.class)))
                    .thenReturn(returnedArticleId.stream()
                            .map(articleId -> UserFavoriteArticleEntity.builder()
                                    .setId(UserFavoriteArticleId.builder()
                                            .articleId(articleId)
                                            .build())
                                    .build())
                            .toList());

            User user = User.builder()
                    .setEmail(new Email("test@example.com"))
                    .setUsername("test-username")
                    .build();
            List<Article> articles =
                    List.of(Article.builder().setTitle("test-title").build());
            List<Long> response = userFavoriteArticleQueryRepository.findUserArticleFavoriteIn(user, articles);

            assertTrue(response.containsAll(returnedArticleId));
        }

        @Test
        public void findUserArticleFavoriteIn_userIsNull_returnListOfArticleId() {
            User user = null;
            List<Article> articles =
                    List.of(Article.builder().setTitle("test-title").build());
            List<Long> response = userFavoriteArticleQueryRepository.findUserArticleFavoriteIn(user, articles);

            assertTrue(response.isEmpty());
        }
    }

    @Nested
    class GetFavoriteCount {
        @Test
        public void getFavoriteCount_positiveCase_returnFavoriteCount() {
            long article1FavoriteCount = 10L;
            long article2FavoriteCount = 20L;
            when(jpaUserFavoriteArticleRepository.countByArticle(any(ArticleEntity.class)))
                    .thenReturn(article1FavoriteCount)
                    .thenReturn(article2FavoriteCount);

            long article1Id = 1L;
            long article2Id = 2L;
            Article article1 = Article.builder()
                    .setId(article1Id)
                    .setTitle("article 1 title")
                    .build();
            Article article2 = Article.builder()
                    .setId(article2Id)
                    .setTitle("article 2 title")
                    .build();
            List<Article> articles = List.of(article1, article2);
            Map<Long, Long> response = userFavoriteArticleQueryRepository.getFavoriteCount(articles);

            assertNotNull(response.get(article1Id));
            assertNotNull(response.get(article2Id));
            assertNull(response.get(3L));
            assertEquals(article1FavoriteCount, response.get(article1Id));
            assertEquals(article2FavoriteCount, response.get(article2Id));
        }
    }
}
