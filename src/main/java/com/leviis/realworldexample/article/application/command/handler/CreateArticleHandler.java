package com.leviis.realworldexample.article.application.command.handler;

import com.leviis.realworldexample.article.application.command.CreateArticleCommand;
import com.leviis.realworldexample.article.application.port.inbound.CreateArticleUseCase;
import com.leviis.realworldexample.article.application.port.outbound.ArticleCommandRepository;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithBodyAndAuthor;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.tag.domain.Tag;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;

public final class CreateArticleHandler implements CreateArticleUseCase {
    private final TagQueryRepository tagQueryRepository;
    private final ArticleCommandRepository articleCommandRepository;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Repository interfaces are effectively immutable - no internal state is exposed")
    public CreateArticleHandler(
            final TagQueryRepository tagQueryRepository, final ArticleCommandRepository articleCommandRepository) {
        this.tagQueryRepository = tagQueryRepository;
        this.articleCommandRepository = articleCommandRepository;
    }

    @Override
    public ArticleWithBodyAndAuthor execute(final CreateArticleCommand command) {
        final Map<Long, Tag> tagMap = getTagMap(command.tags());
        final Article newArticle =
                articleCommandRepository.create(command.intoArticleDomain(getTagIds(tagMap)), tagMap);
        return ArticleWithBodyAndAuthor.from(command.author(), List.copyOf(tagMap.values()), newArticle);
    }

    private List<Long> getTagIds(final Map<Long, Tag> tagMap) {
        return tagMap.keySet().stream().toList();
    }

    private Map<Long, Tag> getTagMap(final List<String> tags) {
        final List<Tag> foundTags = tagQueryRepository.findAllByNameIn(new HashSet<>(tags));
        return foundTags.stream().collect(Collectors.toMap(Tag::id, Function.identity()));
    }
}
