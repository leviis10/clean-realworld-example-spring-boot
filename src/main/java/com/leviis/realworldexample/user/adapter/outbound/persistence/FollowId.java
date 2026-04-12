package com.leviis.realworldexample.user.adapter.outbound.persistence;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Embeddable
public class FollowId {
    private Long followerId;
    private Long followingId;
}
