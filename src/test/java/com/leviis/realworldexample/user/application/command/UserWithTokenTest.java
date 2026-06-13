package com.leviis.realworldexample.user.application.command;

import static org.junit.jupiter.api.Assertions.*;

import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import org.junit.jupiter.api.Test;

class UserWithTokenTest {
    @Test
    public void from_positiveCase_returnMappedUserWithToken() {
        Email email = new Email("test@example.com");
        String username = "test-username";
        String bio = "test-bio";
        String image = "test-image";
        User user = User.builder()
                .setEmail(email)
                .setUsername(username)
                .setBio(bio)
                .setImage(image)
                .build();
        String token = "token";

        UserWithToken response = UserWithToken.from(user, token);

        assertEquals(email.value(), response.email());
        assertEquals(username, response.username());
        assertEquals(bio, response.bio());
        assertEquals(image, response.image());
        assertEquals(token, response.token());
    }
}
