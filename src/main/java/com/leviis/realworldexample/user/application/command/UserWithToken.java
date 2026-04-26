package com.leviis.realworldexample.user.application.command;

import com.leviis.realworldexample.user.domain.User;

public record UserWithToken(String email, String username, String bio, String image, String token) {
    public static UserWithToken from(final User user, final String token) {
        return builder()
                .setEmail(user.email().value())
                .setUsername(user.username())
                .setBio(user.bio())
                .setImage(user.image())
                .setToken(token)
                .build();
    }

    public static UserWithTokenBuilder builder() {
        return new UserWithTokenBuilder();
    }

    public static final class UserWithTokenBuilder {
        private String email;
        private String username;
        private String bio;
        private String image;
        private String token;

        public UserWithTokenBuilder setEmail(final String email) {
            this.email = email;
            return this;
        }

        public UserWithTokenBuilder setUsername(final String username) {
            this.username = username;
            return this;
        }

        public UserWithTokenBuilder setBio(final String bio) {
            this.bio = bio;
            return this;
        }

        public UserWithTokenBuilder setImage(final String image) {
            this.image = image;
            return this;
        }

        public UserWithTokenBuilder setToken(final String token) {
            this.token = token;
            return this;
        }

        public UserWithToken build() {
            return new UserWithToken(this.email, this.username, this.bio, this.image, this.token);
        }
    }
}
