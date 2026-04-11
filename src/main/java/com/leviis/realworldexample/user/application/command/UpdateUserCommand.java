package com.leviis.realworldexample.user.application.command;

import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.Password;
import com.leviis.realworldexample.user.domain.User;

public final class UpdateUserCommand {
    private final Long id;
    private final Email email;
    private final String username;
    private Password password;
    private final String image;
    private final String bio;

    public UpdateUserCommand(
            final Long id,
            final Email email,
            final String username,
            final Password password,
            final String image,
            final String bio) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.image = image;
        this.bio = bio;
    }

    public User intoUserDomain() {
        return new User(this.id, this.email, this.username, this.bio, this.image, this.password);
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password.getValue();
    }

    public void setHashedPassword(final String hashedPassword) {
        this.password = Password.builder()
                .setValue(this.password.getValue())
                .setHashedPassword(hashedPassword)
                .build();
    }

    public static UpdateUserCommandBuilder builder() {
        return new UpdateUserCommandBuilder();
    }

    public static final class UpdateUserCommandBuilder {
        private Long id;
        private Email email;
        private String username;
        private Password password;
        private String image;
        private String bio;

        public UpdateUserCommandBuilder setId(final Long id) {
            this.id = id;
            return this;
        }

        public UpdateUserCommandBuilder setEmail(final String email) {
            this.email = new Email(email);
            return this;
        }

        public UpdateUserCommandBuilder setUsername(final String username) {
            this.username = username;
            return this;
        }

        public UpdateUserCommandBuilder setPassword(final String password) {
            this.password = new Password(password);
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
}
