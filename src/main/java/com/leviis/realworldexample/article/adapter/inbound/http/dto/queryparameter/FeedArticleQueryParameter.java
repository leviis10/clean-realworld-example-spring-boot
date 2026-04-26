package com.leviis.realworldexample.article.adapter.inbound.http.dto.queryparameter;

import com.leviis.realworldexample.article.application.query.FindAllFeedArticleQuery;
import com.leviis.realworldexample.infrastructure.UserContext;
import com.leviis.realworldexample.infrastructure.constants.PaginationConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public final class FeedArticleQueryParameter {
    @Builder.Default
    private Integer limit = PaginationConstants.DEFAULT_LIMIT;

    @Builder.Default
    private Integer offset = PaginationConstants.DEFAULT_OFFSET;

    public FindAllFeedArticleQuery intoQuery(final UserContext userContext) {
        return FindAllFeedArticleQuery.builder()
                .setUser(userContext.intoUserDomain())
                .setLimit(this.limit)
                .setOffset(this.offset)
                .build();
    }
}
