package com.leviis.realworldexample.comment.application.port.outbound;

import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.comment.domain.Comment;
import java.util.List;

@FunctionalInterface
public interface CommentQueryRepository {
    List<Comment> findAllByArticleSlug(Slug articleSlug);
}
