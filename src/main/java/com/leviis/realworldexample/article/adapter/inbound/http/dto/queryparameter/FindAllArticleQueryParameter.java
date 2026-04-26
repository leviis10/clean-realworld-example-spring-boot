package com.leviis.realworldexample.article.adapter.inbound.http.dto.queryparameter;

import com.leviis.realworldexample.article.application.query.FindAllArticleQuery;
import com.leviis.realworldexample.infrastructure.UserContext;
import com.leviis.realworldexample.infrastructure.constants.PaginationConstants;
import com.leviis.realworldexample.user.domain.User;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public final class FindAllArticleQueryParameter {
    private String tag;
    private String author;
    private String favoriteBy;

    @Builder.Default
    private Integer limit = PaginationConstants.DEFAULT_LIMIT;

    @Builder.Default
    private Integer offset = PaginationConstants.DEFAULT_OFFSET;

    public FindAllArticleQuery intoQuery(@Nullable final UserContext userContext) {
        return FindAllArticleQuery.builder()
                .setUser(getUserId(userContext))
                .setTag(this.tag)
                .setAuthor(this.author)
                .setFavoriteBy(this.favoriteBy)
                .setLimit(this.limit)
                .setOffset(this.offset)
                .build();
    }

    @Nullable
    private User getUserId(@Nullable final UserContext userContext) {
        if (userContext == null) {
            return null;
        }

        return userContext.intoUserDomain();
    }
}
