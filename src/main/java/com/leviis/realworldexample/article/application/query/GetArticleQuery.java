package com.leviis.realworldexample.article.application.query;

import com.leviis.realworldexample.user.domain.User;
import com.leviis.realworldexample.utils.SlugUtils;
import java.util.UUID;

public record GetArticleQuery(User authenticatedUser, String slug, UUID slugId) {
    public static GetArticleQueryBuilder builder() {
        return new GetArticleQueryBuilder();
    }

    public static GetArticleQuery from(final User authenticatedUser, final String slug) {
        return builder()
                .setAuthenticatedUser(authenticatedUser)
                .setSlug(SlugUtils.getTitleFrom(slug))
                .setSlugId(SlugUtils.getIdFrom(slug))
                .build();
    }

    public static final class GetArticleQueryBuilder {
        private User authenticatedUser;
        private String slug;
        private UUID slugId;

        public GetArticleQueryBuilder setAuthenticatedUser(final User authenticatedUser) {
            this.authenticatedUser = authenticatedUser;
            return this;
        }

        public GetArticleQueryBuilder setSlug(final String slug) {
            this.slug = slug;
            return this;
        }

        public GetArticleQueryBuilder setSlugId(final UUID slugId) {
            this.slugId = slugId;
            return this;
        }

        public GetArticleQuery build() {
            return new GetArticleQuery(this.authenticatedUser, this.slug, this.slugId);
        }
    }
}
