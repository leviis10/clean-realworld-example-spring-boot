package com.leviis.realworldexample.article.application.command;

import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.user.domain.User;
import lombok.Builder;

public record UpdateArticleCommand(User authenticatedUser, Slug slug, UpdateDataDto updateDataDto) {
    @Builder
    public record UpdateDataDto(String title, String description, String body) {}

    public static UpdateArticleCommandBuilder builder() {
        return new UpdateArticleCommandBuilder();
    }

    public static final class UpdateArticleCommandBuilder {
        private User authenticatedUser;
        private Slug slug;
        private UpdateDataDto updateDataDto;

        public UpdateArticleCommandBuilder setAuthenticatedUser(final User authenticatedUser) {
            this.authenticatedUser = authenticatedUser;
            return this;
        }

        public UpdateArticleCommandBuilder setSlug(final Slug slug) {
            this.slug = slug;
            return this;
        }

        public UpdateArticleCommandBuilder setUpdateDataDto(final UpdateDataDto updateDataDto) {
            this.updateDataDto = updateDataDto;
            return this;
        }

        public UpdateArticleCommand build() {
            return new UpdateArticleCommand(this.authenticatedUser, this.slug, this.updateDataDto);
        }
    }
}
