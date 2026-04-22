package com.leviis.realworldexample.article.application.query.handler;

import com.leviis.realworldexample.article.application.port.inbound.FindAllArticleUseCase;
import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleQueryRepository;
import com.leviis.realworldexample.article.application.query.ArticleWithDetails;
import com.leviis.realworldexample.article.application.query.FindAllArticleQuery;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class FindAllArticleHandler implements FindAllArticleUseCase {
    private final ArticleQueryRepository articleQueryRepository;
    private final TagQueryRepository tagQueryRepository;
    private final UserQueryRepository userQueryRepository;
    private final UserFavoriteArticleQueryRepository userFavoriteArticleQueryRepository;

    public FindAllArticleHandler(
            final ArticleQueryRepository articleQueryRepository,
            final TagQueryRepository tagQueryRepository,
            final UserQueryRepository userQueryRepository,
            final UserFavoriteArticleQueryRepository userFavoriteArticleQueryRepository) {
        this.articleQueryRepository = articleQueryRepository;
        this.tagQueryRepository = tagQueryRepository;
        this.userQueryRepository = userQueryRepository;
        this.userFavoriteArticleQueryRepository = userFavoriteArticleQueryRepository;
    }

    @Override
    public List<ArticleWithDetails> execute(final FindAllArticleQuery query) {
        var foundArticles = articleQueryRepository.findAll(
                query.tag(), query.author(), query.favoriteBy(), query.limit(), query.offset());
        var foundTags = tagQueryRepository.findAllByIdIn(getTagIdFrom(foundArticles));
        var favoriteArticleId =
                userFavoriteArticleQueryRepository.findUserArticleFavoriteIn(query.user(), foundArticles);
        var getFavoriteCount = userFavoriteArticleQueryRepository.getFavoriteCount(foundArticles);
        var foundAuthors = userQueryRepository.findByIds(getAuthorIdFrom(foundArticles));
        var foundIsFollowingAuthors = userQueryRepository.findIsFollowingIn(query.user(), foundAuthors);

        return ArticleWithDetails.from(
                foundArticles,
                foundTags,
                favoriteArticleId,
                getFavoriteCount,
                foundAuthors,
                query.user(),
                foundIsFollowingAuthors);
    }

    private Set<Long> getTagIdFrom(final List<Article> articles) {
        var result = new HashSet<Long>();

        for (var article : articles) {
            result.addAll(article.tagIds());
        }

        return result;
    }

    private Set<Long> getAuthorIdFrom(final List<Article> articles) {
        var result = new HashSet<Long>();

        for (var article : articles) {
            result.add(article.authorId());
        }

        return result;
    }
}
