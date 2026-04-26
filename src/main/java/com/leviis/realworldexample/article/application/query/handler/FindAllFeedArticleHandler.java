package com.leviis.realworldexample.article.application.query.handler;

import com.leviis.realworldexample.article.application.port.inbound.FindAllFeedArticleUseCase;
import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleQueryRepository;
import com.leviis.realworldexample.article.application.query.ArticleWithDetails;
import com.leviis.realworldexample.article.application.query.FindAllFeedArticleQuery;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.user.application.port.outbound.FollowQueryRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindAllFeedArticleHandler implements FindAllFeedArticleUseCase {
    private final ArticleQueryRepository articleQueryRepository;
    private final FollowQueryRepository followQueryRepository;
    private final TagQueryRepository tagQueryRepository;
    private final UserFavoriteArticleQueryRepository userFavoriteArticleQueryRepository;
    private final UserQueryRepository userQueryRepository;

    public FindAllFeedArticleHandler(
            final ArticleQueryRepository articleQueryRepository,
            final FollowQueryRepository followQueryRepository,
            final TagQueryRepository tagQueryRepository,
            final UserFavoriteArticleQueryRepository userFavoriteArticleQueryRepository,
            final UserQueryRepository userQueryRepository) {
        this.articleQueryRepository = articleQueryRepository;
        this.followQueryRepository = followQueryRepository;
        this.tagQueryRepository = tagQueryRepository;
        this.userFavoriteArticleQueryRepository = userFavoriteArticleQueryRepository;
        this.userQueryRepository = userQueryRepository;
    }

    @Override
    public List<ArticleWithDetails> execute(final FindAllFeedArticleQuery query) {
        var followingIds = followQueryRepository.findAllFollowingIdByFollowerId(
                query.user().id());
        var foundArticles = articleQueryRepository.findAllByAuthorIdIn(followingIds, query.offset(), query.limit());
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
