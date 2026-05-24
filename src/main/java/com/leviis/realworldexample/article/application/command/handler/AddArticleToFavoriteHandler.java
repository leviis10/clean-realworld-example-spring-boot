package com.leviis.realworldexample.article.application.command.handler;

import com.leviis.realworldexample.article.application.command.AddArticleToFavoriteCommand;
import com.leviis.realworldexample.article.application.port.inbound.AddArticleToFavoriteUseCase;
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
public final class AddArticleToFavoriteHandler implements AddArticleToFavoriteUseCase {
    private final UserFavoriteArticleCommandRepository userFavoriteArticleCommandRepository;
    private final UserFavoriteArticleQueryRepository userFavoriteArticleQueryRepository;
    private final UserQueryRepository userQueryRepository;
    private final TagQueryRepository tagQueryRepository;
    private final FollowQueryRepository followQueryRepository;

    @Override
    public ArticleWithBodyAndAuthor execute(final AddArticleToFavoriteCommand command) {
        final Article favoritedArticle = userFavoriteArticleCommandRepository.create(
                command.authenticatedUser().id(), command.articleSlug());
        final List<Tag> favoriteArticleTags =
                tagQueryRepository.findAllByIdIn(new HashSet<>(favoritedArticle.tagIds()));
        final long favoritesCount = userFavoriteArticleQueryRepository.getFavoriteCount(favoritedArticle);
        final User favoriteArticleAuthor = userQueryRepository
                .findById(favoritedArticle.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        final boolean isFollowingFavoriteArticleAuthor =
                followQueryRepository.findIsFollowing(command.authenticatedUser(), favoriteArticleAuthor);

        return ArticleWithBodyAndAuthor.from(
                favoritedArticle,
                favoriteArticleTags,
                true,
                favoritesCount,
                favoriteArticleAuthor,
                isFollowingFavoriteArticleAuthor);
    }
}
