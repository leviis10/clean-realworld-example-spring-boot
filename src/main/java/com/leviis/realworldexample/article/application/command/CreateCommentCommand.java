package com.leviis.realworldexample.article.application.command;

import com.leviis.realworldexample.article.domain.Comment;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.user.domain.User;
import lombok.Builder;

@Builder
public record CreateCommentCommand(Comment comment, Slug slug, User author) {}
