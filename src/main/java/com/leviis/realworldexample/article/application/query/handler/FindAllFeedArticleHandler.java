package com.leviis.realworldexample.article.application.query.handler;

import com.leviis.realworldexample.article.application.port.inbound.FindAllFeedArticleUseCase;
import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleQueryRepository;
import com.leviis.realworldexample.article.application.query.FindAllFeedArticleQuery;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithAuthor;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.application.port.outbound.FollowQueryRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class FindAllFeedArticleHandler implements FindAllFeedArticleUseCase {
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
    public List<ArticleWithAuthor> execute(final FindAllFeedArticleQuery query) {
        final List<Long> followingIds = followQueryRepository.findAllFollowingIdByFollowerId(
                query.user().id());
        final List<Article> foundArticles =
                articleQueryRepository.findAllByAuthorIdIn(followingIds, query.offset(), query.limit());
        final List<Tag> foundTags = tagQueryRepository.findAllByIdIn(getTagIdFrom(foundArticles));
        final List<Long> favoriteArticleId =
                userFavoriteArticleQueryRepository.findUserArticleFavoriteIn(query.user(), foundArticles);
        final Map<Long, Long> getFavoriteCount = userFavoriteArticleQueryRepository.getFavoriteCount(foundArticles);
        final List<User> foundAuthors = userQueryRepository.findByIds(getAuthorIdFrom(foundArticles));
        final List<Long> foundIsFollowingAuthors = userQueryRepository.findIsFollowingIn(query.user(), foundAuthors);

        return ArticleWithAuthor.from(
                foundArticles, foundTags, favoriteArticleId, getFavoriteCount, foundAuthors, foundIsFollowingAuthors);
    }

    private Set<Long> getTagIdFrom(final List<Article> articles) {
        return articles.stream().flatMap(article -> article.tagIds().stream()).collect(Collectors.toSet());
    }

    private Set<Long> getAuthorIdFrom(final List<Article> articles) {
        return articles.stream().map(Article::authorId).collect(Collectors.toSet());
    }
}
