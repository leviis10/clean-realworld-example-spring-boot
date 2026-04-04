package com.leviis.realworldexample.user.adapter.inbound.http.dto.response;

import com.leviis.realworldexample.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterUserResponse {
    private String email;
    private String token;
    private String username;
    private String bio;
    private String image;

    public static RegisterUserResponse from(final User user) {
        return RegisterUserResponse.builder()
                .email(user.getEmail())
                .token(user.getToken())
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }
}
