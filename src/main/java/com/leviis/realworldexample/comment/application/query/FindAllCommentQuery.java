package com.leviis.realworldexample.comment.application.query;

import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.user.domain.User;
import lombok.Builder;
import org.jspecify.annotations.Nullable;

@Builder(setterPrefix = "set")
public record FindAllCommentQuery(@Nullable User user, Slug articleSlug) {}
