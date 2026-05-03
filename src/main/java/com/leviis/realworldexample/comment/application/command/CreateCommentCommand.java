package com.leviis.realworldexample.comment.application.command;

import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.comment.domain.Comment;
import com.leviis.realworldexample.user.domain.User;
import lombok.Builder;

@Builder
public record CreateCommentCommand(Comment comment, Slug slug, User author) {}
