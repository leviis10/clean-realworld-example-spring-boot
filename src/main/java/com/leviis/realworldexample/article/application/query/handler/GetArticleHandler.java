package com.leviis.realworldexample.article.application.query.handler;

import com.leviis.realworldexample.article.application.exceptions.ArticleNotFoundException;
import com.leviis.realworldexample.article.application.port.inbound.GetArticleUseCase;
import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleQueryRepository;
import com.leviis.realworldexample.article.application.query.GetArticleQuery;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithBodyAndAuthor;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.application.port.outbound.FollowQueryRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.User;
import java.util.HashSet;
import java.util.List;

public final class GetArticleHandler implements GetArticleUseCase {
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
        final Slug articleSlug = new Slug(query.slug(), query.slugId());
        final Article foundArticle = articleQueryRepository
                .getBySlug(articleSlug)
                .orElseThrow(() -> new ArticleNotFoundException(articleSlug));
        final List<Tag> foundTags = tagQueryRepository.findAllByIdIn(new HashSet<>(foundArticle.tagIds()));
        final boolean isFavoriteArticle =
                userFavoriteArticleQueryRepository.getIsFavoriteArticle(query.authenticatedUser(), foundArticle.id());
        final long favoritesCount = userFavoriteArticleQueryRepository.getFavoriteCount(foundArticle);
        final User foundAuthor =
                userQueryRepository.findById(foundArticle.authorId()).orElse(null);
        final boolean isFollowingAuthor = followQueryRepository.findIsFollowing(query.authenticatedUser(), foundAuthor);

        return ArticleWithBodyAndAuthor.from(
                foundArticle, foundTags, isFavoriteArticle, favoritesCount, foundAuthor, isFollowingAuthor);
    }
}
