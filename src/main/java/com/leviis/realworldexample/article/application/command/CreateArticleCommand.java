package com.leviis.realworldexample.article.application.command;

import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;

public record CreateArticleCommand(String title, String description, String body, List<String> tags, User author) {
    public CreateArticleCommand(
            final String title,
            final String description,
            final String body,
            final List<String> tags,
            final User author) {
        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = List.copyOf(tags);
        this.author = author;
    }

    public Article intoArticleDomain(final List<Long> tagIds) {
        return Article.builder()
                .setTitle(this.title)
                .setDescription(this.description)
                .setBody(this.body)
                .setAuthorId(this.author.id())
                .setTagIds(tagIds)
                .build();
    }

    public static CreateArticleCommandBuilder builder() {
        return new CreateArticleCommandBuilder();
    }

    public static final class CreateArticleCommandBuilder {
        private String title;
        private String description;
        private String body;
        private List<String> tags;
        private User author;

        public CreateArticleCommandBuilder setTitle(final String title) {
            this.title = title;
            return this;
        }

        public CreateArticleCommandBuilder setDescription(final String description) {
            this.description = description;
            return this;
        }

        public CreateArticleCommandBuilder setBody(final String body) {
            this.body = body;
            return this;
        }

        public CreateArticleCommandBuilder setTags(final List<String> tags) {
            this.tags = List.copyOf(tags);
            return this;
        }

        public CreateArticleCommandBuilder setAuthor(final User author) {
            this.author = author;
            return this;
        }

        public CreateArticleCommand build() {
            return new CreateArticleCommand(this.title, this.description, this.body, this.tags, this.author);
        }
    }
}
