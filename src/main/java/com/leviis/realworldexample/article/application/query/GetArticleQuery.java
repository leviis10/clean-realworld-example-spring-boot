package com.leviis.realworldexample.article.application.query;

import com.leviis.realworldexample.user.domain.User;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Builder(setterPrefix = "set")
public record GetArticleQuery(
        @Nullable User authenticatedUser,
        @NonNull String slug,
        @NonNull UUID slugId) {
    public GetArticleQuery {
        Objects.requireNonNull(slug);
        Objects.requireNonNull(slugId);
    }
}
