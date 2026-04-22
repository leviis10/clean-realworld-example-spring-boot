package com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserFavoriteArticleRepository
        extends JpaRepository<UserFavoriteArticleEntity, UserFavoriteArticleId> {
    List<UserFavoriteArticleEntity> findByUserAndArticleIn(UserEntity user, List<ArticleEntity> articles);

    long countByArticle(ArticleEntity article);
}
