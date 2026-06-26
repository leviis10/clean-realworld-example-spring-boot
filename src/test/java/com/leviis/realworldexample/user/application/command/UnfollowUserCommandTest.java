package com.leviis.realworldexample.user.application.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UnfollowUserCommandTest {
    @Test
    public void constructor_positiveCase_returnUnfollowUserCommand() {
        Long followerId = 1L;
        String followingUsername = "test-following-username";
        UnfollowUserCommand.builder()
                .setFollowerId(followerId)
                .setFollowingUsername(followingUsername)
                .build();
    }

    @Test
    public void constructor_followerIdIsNull_throwNullPointerException() {
        Long followerId = null;
        String followingUsername = "test-following-username";
        assertThrows(NullPointerException.class, () -> UnfollowUserCommand.builder()
                .setFollowerId(followerId)
                .setFollowingUsername(followingUsername)
                .build());
    }

    @Test
    public void constructor_followingUsernameIsNull_throwNullPointerException() {
        Long followerId = 1L;
        String followingUsername = null;
        assertThrows(NullPointerException.class, () -> UnfollowUserCommand.builder()
                .setFollowerId(followerId)
                .setFollowingUsername(followingUsername)
                .build());
    }

    @Test
    public void constructor_followingUsernameIsBlank_throwIllegalArgumentException() {
        Long followerId = 1L;
        String followingUsername = "   ";
        assertThrows(IllegalArgumentException.class, () -> UnfollowUserCommand.builder()
                .setFollowerId(followerId)
                .setFollowingUsername(followingUsername)
                .build());
    }
}
