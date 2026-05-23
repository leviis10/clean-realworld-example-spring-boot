package com.leviis.realworldexample.comment.application.port.inbound;

import com.leviis.realworldexample.comment.application.query.FindAllCommentQuery;
import com.leviis.realworldexample.comment.application.readmodel.CommentWithAuthor;
import java.util.List;

@FunctionalInterface
public interface FindAllCommentUseCase {
    List<CommentWithAuthor> execute(FindAllCommentQuery query);
}
