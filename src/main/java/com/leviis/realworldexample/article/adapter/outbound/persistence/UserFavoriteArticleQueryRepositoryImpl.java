package com.leviis.realworldexample.article.adapter.outbound.persistence;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle.JpaUserFavoriteArticleRepository;
import com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle.UserFavoriteArticleEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle.UserFavoriteArticleId;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleQueryRepository;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserFavoriteArticleQueryRepositoryImpl implements UserFavoriteArticleQueryRepository {
    private final JpaUserFavoriteArticleRepository jpaUserFavoriteArticleRepository;

    @Override
    public List<Long> findUserArticleFavoriteIn(final @Nullable User user, final List<Article> articles) {
        return Optional.ofNullable(user)
                .map(user1 -> {
                    final List<ArticleEntity> articlesEntity =
                            articles.stream().map(ArticleEntity::from).toList();
                    return jpaUserFavoriteArticleRepository
                            .findByUserAndArticleIn(UserEntity.from(user1), articlesEntity)
                            .stream()
                            .map(entity -> entity.getId().getArticleId())
                            .toList();
                })
                .orElse(List.of());
    }

    @Override
    public Map<Long, Long> getFavoriteCount(final List<Article> articles) {
        final Map<Long, Long> result = new ConcurrentHashMap<>();

        for (final Article article : articles) {
            final long getArticleFavoriteCount =
                    jpaUserFavoriteArticleRepository.countByArticle(ArticleEntity.from(article));
            result.put(article.id(), getArticleFavoriteCount);
        }

        return result;
    }

    @Override
    public long getFavoriteCount(final Article article) {
        return jpaUserFavoriteArticleRepository.countByArticle(ArticleEntity.from(article));
    }

    @Override
    public boolean getIsFavoriteArticle(@Nullable final User user, final Long articleId) {
        return Optional.ofNullable(user)
                .map(u -> {
                    final Optional<UserFavoriteArticleEntity> foundData =
                            jpaUserFavoriteArticleRepository.findById(UserFavoriteArticleId.builder()
                                    .userId(u.id())
                                    .articleId(articleId)
                                    .build());
                    return foundData.isPresent();
                })
                .orElse(false);
    }
}
