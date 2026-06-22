package com.leviis.realworldexample.user.domain;

import java.util.Objects;
import lombok.Builder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Builder(setterPrefix = "set", toBuilder = true)
public record User(
        @Nullable Long id,
        @NonNull Email email,
        @NonNull String username,
        @Nullable String bio,
        @Nullable String image,
        @Nullable String password) {
    public User {
        Objects.requireNonNull(email);
        Objects.requireNonNull(username);
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
}
