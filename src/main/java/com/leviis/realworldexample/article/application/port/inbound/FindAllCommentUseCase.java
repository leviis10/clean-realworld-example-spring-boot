package com.leviis.realworldexample.article.application.port.inbound;

import com.leviis.realworldexample.article.application.query.FindAllCommentQuery;
import com.leviis.realworldexample.article.application.readmodel.CommentWithAuthor;
import java.util.List;

@FunctionalInterface
public interface FindAllCommentUseCase {
    List<CommentWithAuthor> execute(FindAllCommentQuery query);
}
