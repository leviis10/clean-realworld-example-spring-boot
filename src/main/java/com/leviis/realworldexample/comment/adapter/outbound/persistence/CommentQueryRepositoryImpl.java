package com.leviis.realworldexample.comment.adapter.outbound.persistence;

import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.comment.adapter.outbound.persistence.comment.CommentEntity;
import com.leviis.realworldexample.comment.adapter.outbound.persistence.comment.JpaCommentRepository;
import com.leviis.realworldexample.comment.application.port.outbound.CommentQueryRepository;
import com.leviis.realworldexample.comment.domain.Comment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentQueryRepositoryImpl implements CommentQueryRepository {
    private final JpaCommentRepository jpaCommentRepository;

    @Override
    public List<Comment> findAllByArticleSlug(final Slug articleSlug) {
        final List<CommentEntity> foundComments =
                jpaCommentRepository.findAllByArticleSlug(articleSlug.value(), articleSlug.id());
        return foundComments.stream()
                .map(commentEntity -> commentEntity.into(Comment.class))
                .toList();
    }
}
