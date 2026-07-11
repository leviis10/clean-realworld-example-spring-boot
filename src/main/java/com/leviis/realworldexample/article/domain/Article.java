package com.leviis.realworldexample.article.domain;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.Builder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Builder(setterPrefix = "set", toBuilder = true)
public record Article(
        Long id,
        Slug slug,
        String title,
        String description,
        String body,
        Long authorId,
        List<Long> tagIds,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
    public Article(
            final Long id,
            @Nullable final Slug slug,
            @NonNull final String title,
            final String description,
            final String body,
            final Long authorId,
            @Nullable final List<Long> tagIds,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt) {
        Objects.requireNonNull(title);

        this.id = id;
        this.slug = Optional.ofNullable(slug).orElse(Slug.from(title));
        this.title = title;
        this.description = description;
        this.body = body;
        this.authorId = authorId;
        this.tagIds = Optional.ofNullable(tagIds).map(List::copyOf).orElse(List.of());
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public List<Long> tagIds() {
        return List.copyOf(this.tagIds);
    }
}
