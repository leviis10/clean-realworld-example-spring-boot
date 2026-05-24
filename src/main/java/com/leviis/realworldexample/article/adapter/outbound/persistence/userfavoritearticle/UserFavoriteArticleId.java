package com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle;

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
public class UserFavoriteArticleId {
    private Long userId;

    private Long articleId;

    public static UserFavoriteArticleId from(final Long userId, final Long articleId) {
        return UserFavoriteArticleId.builder()
                .userId(userId)
                .articleId(articleId)
                .build();
    }
}
