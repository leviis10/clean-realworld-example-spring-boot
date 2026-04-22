package com.leviis.realworldexample.article.adapter.outbound.persistence.articletag;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Embeddable
public class ArticleTagId {
    private Long articleId;

    private Long tagId;
}
