package com.leviis.realworldexample.article.application.port.outbound;

import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Map;

public interface UserFavoriteArticleQueryRepository {
    List<Long> findUserArticleFavoriteIn(User user, List<Article> articles);

    Map<Long, Long> getFavoriteCount(List<Article> articles);

    long getFavoriteCount(Article article);

    boolean getIsFavoriteArticle(User user, Long articleId);
}
