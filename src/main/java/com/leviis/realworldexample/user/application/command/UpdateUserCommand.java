package com.leviis.realworldexample.user.application.command;

import com.leviis.realworldexample.user.domain.User;

public record UpdateUserCommand(Long id, String email, String username, String password, String image, String bio) {
    public static UpdateUserCommandBuilder builder() {
        return new UpdateUserCommandBuilder();
    }

    public static final class UpdateUserCommandBuilder {
        private Long id;
        private String email;
        private String username;
        private String password;
        private String image;
        private String bio;

        public UpdateUserCommandBuilder setId(final Long id) {
            this.id = id;
            return this;
        }

        public UpdateUserCommandBuilder setEmail(final String email) {
            this.email = email;
            return this;
        }

        public UpdateUserCommandBuilder setUsername(final String username) {
            this.username = username;
            return this;
        }

        public UpdateUserCommandBuilder setPassword(final String password) {
            this.password = password;
            return this;
        }

        public UpdateUserCommandBuilder setImage(final String image) {
            this.image = image;
            return this;
        }

        public UpdateUserCommandBuilder setBio(final String bio) {
            this.bio = bio;
            return this;
        }

        public UpdateUserCommand build() {
            return new UpdateUserCommand(this.id, this.email, this.username, this.password, this.image, this.bio);
        }
    }

    public User intoUserDomain() {
        return intoUserDomain(this.password);
    }

    public User intoUserDomain(final String hashedPassword) {
        return User.builder()
                .setId(this.id)
                .setEmail(this.email)
                .setUsername(this.username)
                .setBio(this.bio)
                .setImage(this.image)
                .setPassword(hashedPassword)
                .build();
    }
}
