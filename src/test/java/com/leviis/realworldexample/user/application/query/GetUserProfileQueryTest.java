package com.leviis.realworldexample.user.application.query;

import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetUserProfileQueryTest {
    @Test
    public void constructor_positiveCase_constructed() {
        GetUserProfileQuery.builder()
                .setUser(User.builder()
                        .setEmail(new Email("test@example.com"))
                        .setUsername("test-username")
                        .build())
                .setUsername("test-username")
                .build();
    }

    @Test
    public void constructor_missingUser_constructed() {
        GetUserProfileQuery.builder().setUser(null).setUsername("test-username").build();
    }

    @Test
    public void constructor_missingUsername_throwNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> GetUserProfileQuery.builder()
                        .setUser(User.builder()
                                .setEmail(new Email("test@example.com"))
                                .setUsername("test-username")
                                .build())
                        .setUsername(null)
                        .build());
    }
}
