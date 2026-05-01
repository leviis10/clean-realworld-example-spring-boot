package com.leviis.realworldexample.article.adapter.outbound.persistence.articletag;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Embeddable
public class ArticleTagId {
    private Long articleId;

    private Long tagId;

    public static ArticleTagId from(@Nullable final Long articleId, @Nullable final Long tagId) {
        return ArticleTagId.builder().articleId(articleId).tagId(tagId).build();
    }

    public static ArticleTagId from(final Long tagId) {
        return from(null, tagId);
    }
}
