package com.leviis.realworldexample.article.adapter.outbound.persistence;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.article.JpaArticleRepository;
import com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle.JpaUserFavoriteArticleRepository;
import com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle.UserFavoriteArticleEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle.UserFavoriteArticleId;
import com.leviis.realworldexample.article.application.exceptions.ArticleNotFoundException;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleCommandRepository;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class UserFavoriteArticleCommandRepositoryImpl implements UserFavoriteArticleCommandRepository {
    private final JpaUserFavoriteArticleRepository jpaUserFavoriteArticleRepository;
    private final JpaArticleRepository jpaArticleRepository;

    @Override
    @Transactional
    public Article create(final long authenticatedUserId, final Slug articleSlug) {
        final ArticleEntity foundArticle = jpaArticleRepository
                .getBySlugAndSlugId(articleSlug.value(), articleSlug.id())
                .orElseThrow(() -> new ArticleNotFoundException(new Slug(articleSlug.value(), articleSlug.id())));
        final UserFavoriteArticleEntity favoriteArticleData = UserFavoriteArticleEntity.builder()
                .setId(UserFavoriteArticleId.builder()
                        .userId(authenticatedUserId)
                        .articleId(foundArticle.getId())
                        .build())
                .setUser(UserEntity.builder().id(authenticatedUserId).build())
                .setArticle(foundArticle)
                .build();
        final UserFavoriteArticleEntity savedFavoriteArticle =
                jpaUserFavoriteArticleRepository.save(favoriteArticleData);

        return savedFavoriteArticle.getArticle().into(Article.class);
    }

    @Override
    public void delete(final long authenticatedUserId, final long articleId) {
        jpaUserFavoriteArticleRepository.deleteById(UserFavoriteArticleId.from(authenticatedUserId, articleId));
    }
}
