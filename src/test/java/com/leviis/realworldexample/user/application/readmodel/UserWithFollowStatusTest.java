package com.leviis.realworldexample.user.application.readmodel;

import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserWithFollowStatusTest {
    @Test
    void from_positiveCase_returnUserWithFollowStatus() {
        User user = User.builder()
                .setEmail(new Email("test@example.com"))
                .setUsername("test-username")
                .setBio("test-bio")
                .setImage("test-image")
                .build();
        boolean isFollowing = true;
        UserWithFollowStatus response = UserWithFollowStatus.from(user, isFollowing);

        assertEquals(user.username(), response.username());
        assertEquals(user.bio(), response.bio());
        assertEquals(user.image(), response.image());
        assertEquals(isFollowing, response.isFollowing());
    }
}
