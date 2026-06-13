package com.leviis.realworldexample.user.adapter.outbound.persistence.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import org.junit.jupiter.api.Test;

public class UserEntityTest {
    @Test
    public void shouldMapCorrectlyWhenConvertedIntoDomain() {
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .email("test@example.com")
                .username("test-username")
                .bio("test-bio")
                .image("test-image")
                .password("Qwerty123!")
                .build();

        User user = userEntity.intoDomain();

        assertEquals(1L, user.id());
        assertEquals(new Email("test@example.com"), user.email());
        assertEquals("test-username", user.username());
        assertEquals("test-bio", user.bio());
        assertEquals("test-image", user.image());
        assertEquals("Qwerty123!", user.password());
    }
}
