package com.leviis.realworldexample.article.adapter.outbound.persistence;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle.JpaUserFavoriteArticleRepository;
import com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle.UserFavoriteArticleId;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleQueryRepository;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.domain.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserFavoriteArticleQueryRepositoryImpl implements UserFavoriteArticleQueryRepository {
    private final JpaUserFavoriteArticleRepository jpaUserFavoriteArticleRepository;

    @Override
    public List<Long> findUserArticleFavoriteIn(final User user, final List<Article> articles) {
        if (user == null) {
            return List.of();
        }

        var articlesEntity = articles.stream().map(ArticleEntity::from).toList();
        return jpaUserFavoriteArticleRepository.findByUserAndArticleIn(UserEntity.from(user), articlesEntity).stream()
                .map(entity -> entity.getId().getArticleId())
                .toList();
    }

    @Override
    public Map<Long, Long> getFavoriteCount(final List<Article> articles) {
        var result = new HashMap<Long, Long>();

        for (var article : articles) {
            var getArticleFavoriteCount = jpaUserFavoriteArticleRepository.countByArticle(ArticleEntity.from(article));
            result.put(article.id(), getArticleFavoriteCount);
        }

        return result;
    }

    @Override
    public long getFavoriteCount(final Article article) {
        return jpaUserFavoriteArticleRepository.countByArticle(ArticleEntity.from(article));
    }

    @Override
    public boolean getIsFavoriteArticle(final User user, final Long articleId) {
        var foundData = jpaUserFavoriteArticleRepository.findById(UserFavoriteArticleId.builder()
                .userId(user.id())
                .articleId(articleId)
                .build());
        return foundData.isPresent();
    }
}
