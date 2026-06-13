package com.leviis.realworldexample.user.domain;

import lombok.Builder;

@Builder(setterPrefix = "set", toBuilder = true)
public record User(Long id, Email email, String username, String bio, String image, String password) {
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
