package com.leviis.realworldexample.user.application.command;

import java.util.Objects;
import lombok.Builder;
import org.jspecify.annotations.NonNull;

@Builder(setterPrefix = "set")
public record UnfollowUserCommand(
        @NonNull Long followerId, @NonNull String followingUsername) {
    public UnfollowUserCommand {
        Objects.requireNonNull(followerId);
        Objects.requireNonNull(followingUsername);
        if (followingUsername.isBlank()) {
            throw new IllegalArgumentException("Following username cannot be blank");
        }
    }
}
