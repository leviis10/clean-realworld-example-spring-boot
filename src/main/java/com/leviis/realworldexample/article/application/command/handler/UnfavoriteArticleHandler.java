package com.leviis.realworldexample.article.application.command.handler;

import com.leviis.realworldexample.article.application.command.UnfavoriteArticleCommand;
import com.leviis.realworldexample.article.application.port.inbound.UnfavoriteArticleUseCase;
import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleCommandRepository;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleQueryRepository;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithBodyAndAuthor;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.application.port.outbound.FollowQueryRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.User;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UnfavoriteArticleHandler implements UnfavoriteArticleUseCase {
    private final UserFavoriteArticleCommandRepository userFavoriteArticleCommandRepository;
    private final UserFavoriteArticleQueryRepository userFavoriteArticleQueryRepository;
    private final UserQueryRepository userQueryRepository;
    private final TagQueryRepository tagQueryRepository;
    private final FollowQueryRepository followQueryRepository;
    private final ArticleQueryRepository articleQueryRepository;

    @Override
    public ArticleWithBodyAndAuthor execute(final UnfavoriteArticleCommand command) {
        final Article foundArticle = articleQueryRepository
                .getBySlug(command.articleSlug())
                .orElseThrow(() -> new RuntimeException("Article not found"));
        userFavoriteArticleCommandRepository.delete(command.authenticatedUser().id(), foundArticle.id());
        final List<Tag> foundArticleTags = tagQueryRepository.findAllByIdIn(new HashSet<>(foundArticle.tagIds()));
        final long articleFavoritesCount = userFavoriteArticleQueryRepository.getFavoriteCount(foundArticle);
        final User foundArticleAuthor = userQueryRepository
                .findById(foundArticle.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        final boolean isFollowingArticleAuthor =
                followQueryRepository.findIsFollowing(command.authenticatedUser(), foundArticleAuthor);

        return ArticleWithBodyAndAuthor.from(
                foundArticle,
                foundArticleTags,
                false,
                articleFavoritesCount,
                foundArticleAuthor,
                isFollowingArticleAuthor);
    }
}
