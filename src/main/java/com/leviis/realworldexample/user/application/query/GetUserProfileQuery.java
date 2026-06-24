package com.leviis.realworldexample.user.application.query;

import com.leviis.realworldexample.user.domain.User;
import java.util.Objects;
import lombok.Builder;
import org.jspecify.annotations.Nullable;

@Builder(setterPrefix = "set")
public record GetUserProfileQuery(@Nullable User user, String username) {
    public GetUserProfileQuery {
        Objects.requireNonNull(username);
    }
}
