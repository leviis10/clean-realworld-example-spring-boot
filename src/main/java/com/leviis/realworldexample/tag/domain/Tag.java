package com.leviis.realworldexample.tag.domain;

public record Tag(Long id, String name) {
    public static TagBuilder builder() {
        return new TagBuilder();
    }

    public static final class TagBuilder {
        private Long id;
        private String name;

        public TagBuilder setId(final Long id) {
            this.id = id;
            return this;
        }

        public TagBuilder setName(final String name) {
            this.name = name;
            return this;
        }

        public Tag build() {
            return new Tag(this.id, this.name);
        }
    }
}
