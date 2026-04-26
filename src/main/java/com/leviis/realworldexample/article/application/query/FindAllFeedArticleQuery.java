package com.leviis.realworldexample.article.application.query;

import com.leviis.realworldexample.user.domain.User;

public record FindAllFeedArticleQuery(User user, int limit, int offset) {
    public static FindAllFeedArticleQueryBuilder builder() {
        return new FindAllFeedArticleQueryBuilder();
    }

    public static final class FindAllFeedArticleQueryBuilder {
        private User user;
        private int limit;
        private int offset;

        public FindAllFeedArticleQueryBuilder setUser(final User user) {
            this.user = user;
            return this;
        }

        public FindAllFeedArticleQueryBuilder setLimit(final int limit) {
            this.limit = limit;
            return this;
        }

        public FindAllFeedArticleQueryBuilder setOffset(final int offset) {
            this.offset = offset;
            return this;
        }

        public FindAllFeedArticleQuery build() {
            return new FindAllFeedArticleQuery(this.user, this.limit, this.offset);
        }
    }
}
