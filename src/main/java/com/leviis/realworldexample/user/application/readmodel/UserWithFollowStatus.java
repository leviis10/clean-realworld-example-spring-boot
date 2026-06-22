package com.leviis.realworldexample.user.application.readmodel;

import com.leviis.realworldexample.user.domain.User;
import lombok.Builder;

@Builder(setterPrefix = "set")
public record UserWithFollowStatus(String username, String bio, String image, boolean isFollowing) {
    public static UserWithFollowStatus from(final User user, final boolean isFollowing) {
        return UserWithFollowStatus.builder()
                .setUsername(user.username())
                .setBio(user.bio())
                .setImage(user.image())
                .setIsFollowing(isFollowing)
                .build();
    }
}
