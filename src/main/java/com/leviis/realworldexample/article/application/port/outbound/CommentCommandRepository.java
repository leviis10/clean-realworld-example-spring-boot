package com.leviis.realworldexample.article.application.port.outbound;

import com.leviis.realworldexample.article.domain.Comment;
import com.leviis.realworldexample.article.domain.Slug;

public interface CommentCommandRepository {
    Comment create(Comment comment);

    void deleteByIdAndArticleSlug(Long userId, Long commentId, Slug slug);
}
