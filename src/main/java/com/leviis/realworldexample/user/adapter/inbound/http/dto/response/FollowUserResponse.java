package com.leviis.realworldexample.user.adapter.inbound.http.dto.response;

import com.leviis.realworldexample.user.application.readmodel.UserWithFollowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FollowUserResponse {
    private String username;
    private String bio;
    private String image;
    private Boolean isFollowing;

    public static FollowUserResponse from(final UserWithFollowStatus user) {
        return FollowUserResponse.builder()
                .username(user.username())
                .bio(user.bio())
                .image(user.image())
                .isFollowing(user.isFollowing())
                .build();
    }
}
