package com.leviis.realworldexample.article.application.command.handler;

import com.leviis.realworldexample.article.application.command.UpdateArticleCommand;
import com.leviis.realworldexample.article.application.port.inbound.UpdateArticleUseCase;
import com.leviis.realworldexample.article.application.port.outbound.ArticleCommandRepository;
import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleQueryRepository;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithBodyAndAuthor;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.tag.domain.Tag;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;

public final class UpdateArticleHandler implements UpdateArticleUseCase {
    private final ArticleCommandRepository articleCommandRepository;
    private final ArticleQueryRepository articleQueryRepository;
    private final TagQueryRepository tagQueryRepository;
    private final UserFavoriteArticleQueryRepository userFavoriteArticleQueryRepository;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Repository interfaces are effectively immutable — no internal state is exposed")
    public UpdateArticleHandler(
            final ArticleQueryRepository articleQueryRepository,
            final ArticleCommandRepository articleCommandRepository,
            final TagQueryRepository tagQueryRepository,
            final UserFavoriteArticleQueryRepository userFavoriteArticleQueryRepository) {
        this.articleQueryRepository = articleQueryRepository;
        this.articleCommandRepository = articleCommandRepository;
        this.tagQueryRepository = tagQueryRepository;
        this.userFavoriteArticleQueryRepository = userFavoriteArticleQueryRepository;
    }

    @Override
    public ArticleWithBodyAndAuthor execute(final UpdateArticleCommand command) {
        final Article foundArticle = articleQueryRepository
                .getByAuthorAndSlug(command.authenticatedUser(), command.slug())
                .orElseThrow(() -> new RuntimeException("Article not found"));
        final Article updatedArticle =
                articleCommandRepository.save(updateArticle(foundArticle, command.updateDataDto()));
        final List<Tag> foundTags = tagQueryRepository.findAllByIdIn(new HashSet<>(foundArticle.tagIds()));
        final boolean isFavorite = userFavoriteArticleQueryRepository.getIsFavoriteArticle(
                command.authenticatedUser(), updatedArticle.id());
        final long favoritesCount = userFavoriteArticleQueryRepository.getFavoriteCount(updatedArticle);

        return ArticleWithBodyAndAuthor.from(
                command.authenticatedUser(), foundTags, isFavorite, favoritesCount, updatedArticle);
    }

    private Article updateArticle(final Article article, final UpdateArticleCommand.UpdateDataDto updateData) {
        final Article.ArticleBuilder articleBuilder = article.intoBuilder()
                .setDescription(updateData.description())
                .setBody(updateData.body())
                .setUpdatedAt(OffsetDateTime.now());

        if (!updateData.title().equals(article.title())) {
            articleBuilder
                    .setTitle(updateData.title())
                    .setSlug(Slug.from(updateData.title(), article.slug().id()));
        }

        return articleBuilder.build();
    }
}
