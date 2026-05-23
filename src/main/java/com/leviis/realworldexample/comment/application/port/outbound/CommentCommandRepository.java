package com.leviis.realworldexample.comment.application.port.outbound;

import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.comment.domain.Comment;

public interface CommentCommandRepository {
    Comment create(Comment comment);

    void deleteByIdAndArticleSlug(Long userId, Long commentId, Slug slug);
}
