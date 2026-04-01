package com.leviis.realworldexample.comment.domain;

import java.time.OffsetDateTime;

public class Comment {
    private String body;
    private Long authorId;
    private Long articleId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
