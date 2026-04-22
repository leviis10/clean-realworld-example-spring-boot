package com.leviis.realworldexample.article.application.query;

import com.leviis.realworldexample.user.domain.User;

public record FindAllArticleQuery(User user, String tag, String author, String favoriteBy, int limit, int offset) {
    public static FindAllArticleQueryBuilder builder() {
        return new FindAllArticleQueryBuilder();
    }

    public static final class FindAllArticleQueryBuilder {
        private User user;
        private String tag;
        private String author;
        private String favoriteBy;
        private int limit;
        private int offset;

        public FindAllArticleQueryBuilder setUser(final User user) {
            this.user = user;
            return this;
        }

        public FindAllArticleQueryBuilder setTag(final String tag) {
            this.tag = tag;
            return this;
        }

        public FindAllArticleQueryBuilder setAuthor(final String author) {
            this.author = author;
            return this;
        }

        public FindAllArticleQueryBuilder setFavoriteBy(final String favoriteBy) {
            this.favoriteBy = favoriteBy;
            return this;
        }

        public FindAllArticleQueryBuilder setLimit(final int limit) {
            this.limit = limit;
            return this;
        }

        public FindAllArticleQueryBuilder setOffset(final int offset) {
            this.offset = offset;
            return this;
        }

        public FindAllArticleQuery build() {
            return new FindAllArticleQuery(this.user, this.tag, this.author, this.favoriteBy, this.limit, this.offset);
        }
    }
}
