package com.leviis.realworldexample.user.adapter.outbound.persistence.follow;

import static org.junit.jupiter.api.Assertions.*;

import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import org.junit.jupiter.api.Test;

class FollowEntityTest {
    @Test
    public void from_positiveCase_returnFollowEntity() {
        Long followerId = 1L;
        Long followingId = 2L;
        FollowEntity response = FollowEntity.from(
                UserEntity.builder().id(followerId).build(),
                UserEntity.builder().id(followingId).build());

        assertEquals(followerId, response.getId().getFollowerId());
        assertEquals(followerId, response.getFollower().getId());
        assertEquals(followingId, response.getId().getFollowingId());
        assertEquals(followingId, response.getFollowing().getId());
    }

    @Test
    public void from_followerIdIsNull_throwNullPointerException() {
        Long followerId = null;
        Long followingId = 2L;

        assertThrows(
                NullPointerException.class,
                () -> FollowEntity.from(
                        UserEntity.builder().id(followerId).build(),
                        UserEntity.builder().id(followingId).build()));
    }

    @Test
    public void from_followingIdIsNull_throwNullPointerException() {
        Long followerId = 1L;
        Long followingId = null;

        assertThrows(
                NullPointerException.class,
                () -> FollowEntity.from(
                        UserEntity.builder().id(followerId).build(),
                        UserEntity.builder().id(followingId).build()));
    }

    @Test
    public void from_sameFollowerAndFollowingId_throwIllegalArgumentException() {
        Long followerId = 1L;
        Long followingId = 1L;

        assertThrows(
                IllegalArgumentException.class,
                () -> FollowEntity.from(
                        UserEntity.builder().id(followerId).build(),
                        UserEntity.builder().id(followingId).build()));
    }
}
