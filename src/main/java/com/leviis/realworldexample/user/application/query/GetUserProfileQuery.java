package com.leviis.realworldexample.user.application.query;

import com.leviis.realworldexample.user.domain.User;
import lombok.Builder;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@Builder(setterPrefix = "set")
public record GetUserProfileQuery(@Nullable User user, String username) {
    public GetUserProfileQuery {
        Objects.requireNonNull(username);
    }
}
