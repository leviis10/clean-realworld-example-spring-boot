package com.leviis.realworldexample.user.adapter.inbound.http.dto.response;

import com.leviis.realworldexample.infrastructure.UserContext;
import com.leviis.realworldexample.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponse {
    private String email;
    private String token;
    private String username;
    private String bio;
    private String image;

    public static UserResponse from(final User user) {
        return from(user, user.getToken());
    }

    public static UserResponse from(final User user, final String token) {
        return UserResponse.builder()
                .email(user.getEmail())
                .token(token)
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }

    public static UserResponse from(final UserContext user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .token(user.getToken())
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }
}
