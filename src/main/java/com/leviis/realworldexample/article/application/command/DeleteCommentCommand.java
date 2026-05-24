package com.leviis.realworldexample.article.application.command;

import com.leviis.realworldexample.article.domain.Slug;
import lombok.Builder;

@Builder(setterPrefix = "set")
public record DeleteCommentCommand(Long authenticatedUserId, Long commentId, Slug articleSlug) {}
