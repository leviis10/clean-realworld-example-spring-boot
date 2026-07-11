package com.leviis.realworldexample.user.adapter.outbound.persistence.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UserEntityTest {
    @Nested
    class IntoDomain {
        @Test
        public void intoDomain_positiveCase_returnUserDomain() {
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

    @Nested
    class From {
        @Nested
        class UserParameter {
            @Test
            public void from_positiveCase_returnMappedUserEntity() {
                long id = 1L;
                Email email = new Email("test@example.com");
                String username = "test-username";
                String bio = "test-bio";
                String image = "test-image";
                String password = "Qwerty123!";
                User user = User.builder()
                        .setId(id)
                        .setEmail(email)
                        .setUsername(username)
                        .setBio(bio)
                        .setImage(image)
                        .setPassword(password)
                        .build();

                UserEntity response = UserEntity.from(user);

                assertEquals(user.id(), response.getId());
                assertEquals(user.email().value(), response.getEmail());
                assertEquals(user.username(), response.getUsername());
                assertEquals(user.bio(), response.getBio());
                assertEquals(user.image(), response.getImage());
                assertEquals(user.password(), response.getPassword());
            }
        }
    }
}
