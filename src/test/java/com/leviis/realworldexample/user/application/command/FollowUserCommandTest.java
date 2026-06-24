package com.leviis.realworldexample.user.application.command;

import static org.junit.jupiter.api.Assertions.*;

import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import org.junit.jupiter.api.Test;

class FollowUserCommandTest {
    @Test
    public void constructor_positiveCase_success() {
        FollowUserCommand.builder()
                .setFollower(User.builder()
                        .setId(1L)
                        .setUsername("test-follower")
                        .setEmail(new Email("follower@example.com"))
                        .build())
                .setFollowingUsername("test-following")
                .build();
    }

    @Test
    public void constructor_followerIdIsNull_success() {
        assertThrows(NullPointerException.class, () -> FollowUserCommand.builder()
                .setFollower(User.builder()
                        .setUsername("test-follower")
                        .setEmail(new Email("follower@example.com"))
                        .build())
                .setFollowingUsername("test-following")
                .build());
    }

    @Test
    public void constructor_followerIsNull_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> FollowUserCommand.builder()
                .setFollower(null)
                .setFollowingUsername("test-following")
                .build());
    }

    @Test
    public void constructor_followingUsernameIsNull_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> FollowUserCommand.builder()
                .setFollower(User.builder()
                        .setId(1L)
                        .setUsername("test-follower")
                        .setEmail(new Email("follower@example.com"))
                        .build())
                .setFollowingUsername(null)
                .build());
    }

    @Test
    public void constructor_followingUsernameIsBlank_throwNullPointerException() {
        assertThrows(IllegalArgumentException.class, () -> FollowUserCommand.builder()
                .setFollower(User.builder()
                        .setId(1L)
                        .setUsername("test-follower")
                        .setEmail(new Email("follower@example.com"))
                        .build())
                .setFollowingUsername("   ")
                .build());
    }
}
