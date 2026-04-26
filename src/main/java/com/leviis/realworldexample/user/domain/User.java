package com.leviis.realworldexample.user.domain;

public record User(Long id, Email email, String username, String bio, String image, String password) {
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static UserBuilder from(final User user) {
        return builder()
                .setId(user.id)
                .setEmail(user.email)
                .setUsername(user.username)
                .setBio(user.bio)
                .setImage(user.image)
                .setPassword(user.password);
    }

    public static final class UserBuilder {
        private Long id;
        private Email email;
        private String username;
        private String bio;
        private String image;
        private String password;

        public UserBuilder setId(final Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setEmail(final Email email) {
            this.email = email;
            return this;
        }

        public UserBuilder setEmail(final String email) {
            this.email = new Email(email);
            return this;
        }

        public UserBuilder setUsername(final String username) {
            this.username = username;
            return this;
        }

        public UserBuilder setBio(final String bio) {
            this.bio = bio;
            return this;
        }

        public UserBuilder setImage(final String image) {
            this.image = image;
            return this;
        }

        public UserBuilder setPassword(final String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this.id, this.email, this.username, this.bio, this.image, this.password);
        }
    }
}
