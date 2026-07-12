package com.leviis.realworldexample.article.application.query;

import static org.junit.jupiter.api.Assertions.*;

import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class GetArticleQueryTest {
    @Test
    public void constructor_positiveCase_constructed() {
        User user = User.builder()
                .setEmail(new Email("user@example.com"))
                .setUsername("user")
                .build();
        GetArticleQuery.builder()
                .setAuthenticatedUser(user)
                .setSlug("slug")
                .setSlugId(UUID.randomUUID())
                .build();
    }

    @Test
    public void constructor_authenticatedUserIsNull_constructed() {
        GetArticleQuery.builder()
                .setAuthenticatedUser(null)
                .setSlug("slug")
                .setSlugId(UUID.randomUUID())
                .build();
    }

    @Test
    public void constructor_slugIsNull_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> GetArticleQuery.builder()
                .setAuthenticatedUser(null)
                .setSlug(null)
                .setSlugId(UUID.randomUUID())
                .build());
    }

    @Test
    public void constructor_slugIdIsNull_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> GetArticleQuery.builder()
                .setAuthenticatedUser(null)
                .setSlug("slug")
                .setSlugId(null)
                .build());
    }
}
