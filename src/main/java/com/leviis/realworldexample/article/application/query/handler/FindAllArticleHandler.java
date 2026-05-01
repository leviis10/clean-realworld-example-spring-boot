package com.leviis.realworldexample.article.application.query.handler;

import com.leviis.realworldexample.article.application.port.inbound.FindAllArticleUseCase;
import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleQueryRepository;
import com.leviis.realworldexample.article.application.query.FindAllArticleQuery;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithAuthor;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    public List<ArticleWithAuthor> execute(final FindAllArticleQuery query) {
        final List<Article> foundArticles = articleQueryRepository.findAll(
                query.tag(), query.author(), query.favoriteBy(), query.limit(), query.offset());
        final List<Tag> foundTags = tagQueryRepository.findAllByIdIn(getTagIdFrom(foundArticles));
        final List<Long> favoriteArticleId =
                userFavoriteArticleQueryRepository.findUserArticleFavoriteIn(query.user(), foundArticles);
        final Map<Long, Long> getFavoriteCount = userFavoriteArticleQueryRepository.getFavoriteCount(foundArticles);
        final List<User> foundAuthors = userQueryRepository.findByIds(getAuthorIdFrom(foundArticles));
        final List<Long> foundIsFollowingAuthors = userQueryRepository.findIsFollowingIn(query.user(), foundAuthors);

        return ArticleWithAuthor.from(
                foundArticles,
                foundTags,
                favoriteArticleId,
                getFavoriteCount,
                foundAuthors,
                query.user(),
                foundIsFollowingAuthors);
    }

    private Set<Long> getTagIdFrom(final List<Article> articles) {
        return articles.stream().flatMap(article -> article.tagIds().stream()).collect(Collectors.toSet());
    }

    private Set<Long> getAuthorIdFrom(final List<Article> articles) {
        return articles.stream().map(Article::authorId).collect(Collectors.toSet());
    }
}
