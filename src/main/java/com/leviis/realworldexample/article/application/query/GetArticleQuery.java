package com.leviis.realworldexample.article.application.query;

import com.leviis.realworldexample.user.domain.User;
import java.util.UUID;

public record GetArticleQuery(User authenticatedUser, String slug, UUID slugId) {
    private static final int UUID_MIN_LENGTH = 36;

    public static GetArticleQueryBuilder builder() {
        return new GetArticleQueryBuilder();
    }

    public static GetArticleQuery from(final User authenticatedUser, final String slug) {
        if (slug.length() <= UUID_MIN_LENGTH) {
            throw new IllegalArgumentException("Invalid slug");
        }

        return builder()
                .setAuthenticatedUser(authenticatedUser)
                .setSlug(getSlugFrom(slug))
                .setSlugId(getSlugIdFrom(slug))
                .build();
    }

    private static UUID getSlugIdFrom(final String slug) {
        return UUID.fromString(slug.substring(UUID_MIN_LENGTH));
    }

    private static String getSlugFrom(final String slug) {
        var slugLength = slug.length() - UUID_MIN_LENGTH;

        return slug.substring(0, slugLength + 1);
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
