package com.leviis.realworldexample.comment.application.port.outbound;

import com.leviis.realworldexample.comment.domain.Comment;

@FunctionalInterface
public interface CommentCommandRepository {
    Comment create(Comment comment);
}
