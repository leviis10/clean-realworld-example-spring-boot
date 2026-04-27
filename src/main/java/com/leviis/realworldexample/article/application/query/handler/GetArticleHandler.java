package com.leviis.realworldexample.article.application.query.handler;

import com.leviis.realworldexample.article.application.port.inbound.GetArticleUseCase;
import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleQueryRepository;
import com.leviis.realworldexample.article.application.query.ArticleWithBodyAndAuthor;
import com.leviis.realworldexample.article.application.query.GetArticleQuery;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.user.application.port.outbound.FollowQueryRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import java.util.HashSet;

public class GetArticleHandler implements GetArticleUseCase {
    private final ArticleQueryRepository articleQueryRepository;
    private final TagQueryRepository tagQueryRepository;
    private final UserFavoriteArticleQueryRepository userFavoriteArticleQueryRepository;
    private final UserQueryRepository userQueryRepository;
    private final FollowQueryRepository followQueryRepository;

    public GetArticleHandler(
            final ArticleQueryRepository articleQueryRepository,
            final TagQueryRepository tagQueryRepository,
            final UserFavoriteArticleQueryRepository userFavoriteArticleQueryRepository,
            final UserQueryRepository userQueryRepository,
            final FollowQueryRepository followQueryRepository) {
        this.articleQueryRepository = articleQueryRepository;
        this.tagQueryRepository = tagQueryRepository;
        this.userFavoriteArticleQueryRepository = userFavoriteArticleQueryRepository;
        this.userQueryRepository = userQueryRepository;
        this.followQueryRepository = followQueryRepository;
    }

    @Override
    public ArticleWithBodyAndAuthor execute(final GetArticleQuery query) {
        var foundArticle = articleQueryRepository
                .getBySlug(new Slug(query.slug(), query.slugId()))
                .orElseThrow(() -> new RuntimeException("Article not found"));
        var foundTags = tagQueryRepository.findAllByIdIn(new HashSet<>(foundArticle.tagIds()));
        var isFavoriteArticle =
                userFavoriteArticleQueryRepository.getIsFavoriteArticle(query.authenticatedUser(), foundArticle.id());
        var favoritesCount = userFavoriteArticleQueryRepository.getFavoriteCount(foundArticle);
        var foundAuthor = userQueryRepository.findById(foundArticle.authorId()).orElse(null);
        var isFollowingAuthor = followQueryRepository.findIsFollowing(query.authenticatedUser(), foundAuthor);

        return ArticleWithBodyAndAuthor.from(
                foundArticle, foundTags, isFavoriteArticle, favoritesCount, foundAuthor, isFollowingAuthor);
    }
}
