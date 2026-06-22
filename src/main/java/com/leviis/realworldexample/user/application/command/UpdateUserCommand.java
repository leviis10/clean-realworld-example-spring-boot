package com.leviis.realworldexample.user.application.command;

import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.RawPassword;
import com.leviis.realworldexample.user.domain.User;
import java.util.Objects;
import lombok.Builder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Builder(setterPrefix = "set")
public record UpdateUserCommand(
        @NonNull Long id,
        @NonNull Email email,
        @NonNull String username,
        @Nullable RawPassword password,
        @Nullable String image,
        @Nullable String bio) {
    public UpdateUserCommand {
        Objects.requireNonNull(id);
        Objects.requireNonNull(email);
        Objects.requireNonNull(username);
    }

    public User intoUserDomain(final String hashedPassword) {
        return User.builder()
                .setId(this.id)
                .setEmail(this.email)
                .setUsername(this.username)
                .setBio(this.bio)
                .setImage(this.image)
                .setPassword(hashedPassword)
                .build();
    }
}
