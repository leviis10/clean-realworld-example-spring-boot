package com.leviis.realworldexample.comment.domain;

import java.time.OffsetDateTime;

public record Comment(String body, Long authorId, Long articleId, OffsetDateTime createdAt, OffsetDateTime updatedAt) {}
