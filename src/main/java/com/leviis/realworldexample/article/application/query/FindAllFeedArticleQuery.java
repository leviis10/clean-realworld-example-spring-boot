package com.leviis.realworldexample.article.application.query;

import com.leviis.realworldexample.infrastructure.constants.PaginationConstants;
import com.leviis.realworldexample.user.domain.User;
import java.util.Objects;
import lombok.Builder;
import lombok.NonNull;

@Builder(setterPrefix = "set")
public record FindAllFeedArticleQuery(@NonNull User user, int limit, int offset) {
    public FindAllFeedArticleQuery {
        Objects.requireNonNull(user);

        if (limit < PaginationConstants.MINIMUM_ALLOWED_LIMIT) {
            throw new IllegalArgumentException("Invalid `limit` value. Value must greater than 0");
        }

        if (offset < PaginationConstants.MINIMUM_ALLOWED_OFFSET) {
            throw new IllegalArgumentException("Invalid `offset` value. Value must be 0 or a positive number");
        }
    }
}
