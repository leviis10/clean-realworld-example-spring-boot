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
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

@RequiredArgsConstructor
public final class CreateArticleHandler implements CreateArticleUseCase {
    private final TagQueryRepository tagQueryRepository;
    private final ArticleCommandRepository articleCommandRepository;

    @Override
    public ArticleWithBodyAndAuthor execute(@NonNull final CreateArticleCommand command) {
        Objects.requireNonNull(command);

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
