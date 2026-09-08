package com.leviis.realworldexample.mock;

import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.time.OffsetDateTime;
import java.util.Map;

public class MockAuthor {
    public static final long AUTHOR_1_ID = 1L;
    public static final String AUTHOR_1_EMAIL = "author1@example.com";
    public static final String AUTHOR_1_USERNAME = "author1";
    public static final String AUTHOR_1_PASSWORD = "author1";
    public static final String AUTHOR_1_BIO = "bio for author 1";
    public static final String AUTHOR_1_IMAGE = "image for author 1";

    public static final UserEntity AUTHOR_ENTITY_1 = UserEntity.builder()
            .id(AUTHOR_1_ID)
            .email(AUTHOR_1_EMAIL)
            .username(AUTHOR_1_USERNAME)
            .password(AUTHOR_1_PASSWORD)
            .bio(AUTHOR_1_BIO)
            .image(AUTHOR_1_IMAGE)
            .createdAt(OffsetDateTime.now())
            .updatedAt(OffsetDateTime.now())
            .build();

    public static final User AUTHOR_1 = User.builder()
            .setId(AUTHOR_1_ID)
            .setEmail(new Email(AUTHOR_1_EMAIL))
            .setUsername(AUTHOR_1_USERNAME)
            .setBio(AUTHOR_1_BIO)
            .setImage(AUTHOR_1_IMAGE)
            .setPassword(AUTHOR_1_PASSWORD)
            .build();

    public static Map<Long, UserEntity> AUTHOR_ENTITY_MAP = Map.of(AUTHOR_1_ID, AUTHOR_ENTITY_1);

    public static Map<Long, User> AUTHOR_MAP = Map.of(AUTHOR_1_ID, AUTHOR_1);
}
