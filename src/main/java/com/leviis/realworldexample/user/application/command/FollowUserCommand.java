package com.leviis.realworldexample.user.application.command;

import com.leviis.realworldexample.user.domain.User;
import java.util.Objects;
import lombok.Builder;
import org.jspecify.annotations.NonNull;

@Builder(setterPrefix = "set")
public record FollowUserCommand(
        @NonNull User follower, @NonNull String followingUsername) {
    public FollowUserCommand {
        Objects.requireNonNull(follower);
        Objects.requireNonNull(follower.id());
        Objects.requireNonNull(followingUsername);
        if (followingUsername.isBlank()) {
            throw new IllegalArgumentException("Following Username cannot be blank.");
        }
    }
}
