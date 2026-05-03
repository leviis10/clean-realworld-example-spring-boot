package com.leviis.realworldexample.comment.domain;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder(setterPrefix = "set", toBuilder = true)
public record Comment(
        Long id, String body, Long authorId, Long articleId, LocalDateTime createdAt, LocalDateTime updatedAt) {}
