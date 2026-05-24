package com.leviis.realworldexample.article.adapter.outbound.persistence.comment;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query(
            value = "DELETE c " + "FROM comment c "
                    + "JOIN article a ON c.article_id = a.id "
                    + "WHERE c.id = :id "
                    + "AND c.author_id = :userId "
                    + "AND a.slug = :slug "
                    + "AND a.slug_id = :slugId",
            nativeQuery = true)
    void deleteByIdAndArticleSlug(
            @Param("userId") Long userId,
            @Param("id") Long id,
            @Param("slug") String slug,
            @Param("slugId") String slugId);
}
