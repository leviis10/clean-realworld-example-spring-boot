package com.leviis.realworldexample.article.adapter.inbound.http.dto.queryparameter;

import com.leviis.realworldexample.infrastructure.constants.PaginationConstants;
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
}
