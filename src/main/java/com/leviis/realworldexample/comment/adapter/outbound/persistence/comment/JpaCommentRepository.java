package com.leviis.realworldexample.comment.adapter.outbound.persistence.comment;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("SELECT ce " + "FROM CommentEntity ce "
            + "INNER JOIN ce.article a "
            + "WHERE a.slug = :slug "
            + "AND a.slugId = :slugId")
    List<CommentEntity> findAllByArticleSlug(@Param("slug") String slug, @Param("slugId") UUID slugId);
}
