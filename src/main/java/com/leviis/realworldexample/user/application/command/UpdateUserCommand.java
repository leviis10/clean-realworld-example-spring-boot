package com.leviis.realworldexample.user.application.command;

import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.RawPassword;
import com.leviis.realworldexample.user.domain.User;
import lombok.Builder;

@Builder(setterPrefix = "set")
public record UpdateUserCommand(Long id, Email email, String username, RawPassword password, String image, String bio) {
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
