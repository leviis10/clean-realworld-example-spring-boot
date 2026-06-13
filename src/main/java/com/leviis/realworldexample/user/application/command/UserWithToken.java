package com.leviis.realworldexample.user.application.command;

import com.leviis.realworldexample.user.domain.User;
import lombok.Builder;

@Builder(setterPrefix = "set")
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
}
