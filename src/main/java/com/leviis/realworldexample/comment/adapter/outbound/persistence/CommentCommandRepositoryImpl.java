package com.leviis.realworldexample.comment.adapter.outbound.persistence;

import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.comment.adapter.outbound.persistence.comment.CommentEntity;
import com.leviis.realworldexample.comment.adapter.outbound.persistence.comment.JpaCommentRepository;
import com.leviis.realworldexample.comment.application.port.outbound.CommentCommandRepository;
import com.leviis.realworldexample.comment.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class CommentCommandRepositoryImpl implements CommentCommandRepository {
    private final JpaCommentRepository jpaCommentRepository;

    @Override
    @Transactional
    public Comment create(final Comment comment) {
        final CommentEntity createdComment = jpaCommentRepository.save(CommentEntity.from(comment));
        return createdComment.into(Comment.class);
    }

    @Override
    @Transactional
    public void deleteByIdAndArticleSlug(final Long userId, final Long commentId, final Slug slug) {
        jpaCommentRepository.deleteByIdAndArticleSlug(
                userId, commentId, slug.value(), slug.id().toString());
    }
}
