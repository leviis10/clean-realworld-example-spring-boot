package com.leviis.realworldexample.article.application.port.outbound;

import com.leviis.realworldexample.article.domain.Comment;
import com.leviis.realworldexample.article.domain.Slug;
import java.util.List;

@FunctionalInterface
public interface CommentQueryRepository {
    List<Comment> findAllByArticleSlug(Slug articleSlug);
}
