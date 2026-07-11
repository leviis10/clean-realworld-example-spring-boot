package com.leviis.realworldexample.article.application.query;

import com.leviis.realworldexample.user.domain.User;
import lombok.Builder;
import lombok.NonNull;

import java.util.Objects;

@Builder(setterPrefix = "set")
public record FindAllFeedArticleQuery(@NonNull User user, int limit, int offset) {
    public FindAllFeedArticleQuery {
        Objects.requireNonNull(user);

        if (limit < 1) {
            throw new IllegalArgumentException("Invalid `limit` value. Value must greater than 0");
        }

        if (offset < 0) {
            throw new IllegalArgumentException("Invalid `offset` value. Value must be 0 or a positive number");
        }
    }
}
