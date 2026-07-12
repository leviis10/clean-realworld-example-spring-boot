package com.leviis.realworldexample.article.application.query;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import org.junit.jupiter.api.Test;

class FindAllFeedArticleQueryTest {
    @Test
    public void constructor_positiveCase_returnQueryData() {
        User user = User.builder()
                .setEmail(new Email("user@example.com"))
                .setUsername("user1")
                .build();
        int limit = 10;
        int offset = 0;

        FindAllFeedArticleQuery.builder()
                .setUser(user)
                .setLimit(limit)
                .setOffset(offset)
                .build();
    }

    @Test
    public void constructor_offsetIsNegative_throwIllegalArgumentException() {
        User user = User.builder()
                .setEmail(new Email("user@example.com"))
                .setUsername("user1")
                .build();
        int limit = 10;
        int offset = -1;

        assertThrows(IllegalArgumentException.class, () -> FindAllFeedArticleQuery.builder()
                .setUser(user)
                .setLimit(limit)
                .setOffset(offset)
                .build());
    }

    @Test
    public void constructor_limitBelow1_throwIllegalArgumentException() {
        User user = User.builder()
                .setEmail(new Email("user@example.com"))
                .setUsername("user1")
                .build();
        int limit = 0;
        int offset = 0;

        assertThrows(IllegalArgumentException.class, () -> FindAllFeedArticleQuery.builder()
                .setUser(user)
                .setLimit(limit)
                .setOffset(offset)
                .build());
    }

    @Test
    public void constructor_userIsNull_throwNullPointerException() {
        User user = null;
        int limit = 10;
        int offset = 0;

        assertThrows(NullPointerException.class, () -> FindAllFeedArticleQuery.builder()
                .setUser(user)
                .setLimit(limit)
                .setOffset(offset)
                .build());
    }
}
