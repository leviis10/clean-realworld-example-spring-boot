package com.leviis.realworldexample.article.application.query;

import com.leviis.realworldexample.infrastructure.constants.PaginationConstants;
import com.leviis.realworldexample.user.domain.User;
import java.util.Optional;
import lombok.Builder;
import org.jspecify.annotations.Nullable;

@Builder(setterPrefix = "set")
public record FindAllArticleQuery(
        @Nullable User user,
        @Nullable String tag,
        @Nullable String author,
        @Nullable String favoriteBy,
        Integer limit,
        Integer offset) {
    public FindAllArticleQuery(
            @Nullable final User user,
            @Nullable final String tag,
            @Nullable final String author,
            @Nullable final String favoriteBy,
            @Nullable final Integer limit,
            @Nullable final Integer offset) {
        this.user = user;
        this.tag = tag;
        this.author = author;
        this.favoriteBy = favoriteBy;
        this.limit = Optional.ofNullable(limit).orElse(PaginationConstants.DEFAULT_LIMIT);
        this.offset = Optional.ofNullable(offset).orElse(PaginationConstants.DEFAULT_OFFSET);
    }
}
