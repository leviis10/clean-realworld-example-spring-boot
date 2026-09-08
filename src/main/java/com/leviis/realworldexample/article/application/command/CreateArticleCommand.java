package com.leviis.realworldexample.article.application.command;

import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Builder(setterPrefix = "set")
public record CreateArticleCommand(
        @NonNull String title,
        @NonNull String description,
        @NonNull String body,
        @NonNull List<String> tags,
        @NonNull User author) {
    public CreateArticleCommand(
            @NonNull final String title,
            @NonNull final String description,
            @NonNull final String body,
            @Nullable final List<String> tags,
            @NonNull final User author) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(description);
        Objects.requireNonNull(body);
        Objects.requireNonNull(author);

        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = tags == null ? List.of() : List.copyOf(tags);
        this.author = author;
    }

    public Article intoArticleDomain(final List<Long> tagIds) {
        return Article.builder()
                .setTitle(this.title)
                .setDescription(this.description)
                .setBody(this.body)
                .setAuthorId(this.author.id())
                .setTagIds(tagIds)
                .build();
    }
}
