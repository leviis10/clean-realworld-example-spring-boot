package com.leviis.realworldexample.user.application.readmodel;

import com.leviis.realworldexample.user.domain.User;
import java.util.Objects;
import lombok.Builder;
import org.jspecify.annotations.NonNull;

@Builder(setterPrefix = "set")
public record UserWithFollowStatus(String username, String bio, String image, boolean isFollowing) {
    public static UserWithFollowStatus from(@NonNull final User user, final boolean isFollowing) {
        Objects.requireNonNull(user);

        return UserWithFollowStatus.builder()
                .setUsername(user.username())
                .setBio(user.bio())
                .setImage(user.image())
                .setIsFollowing(isFollowing)
                .build();
    }
}
