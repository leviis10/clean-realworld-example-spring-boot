package com.leviis.realworldexample.article.adapter.outbound.persistence.comment;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.article.domain.Comment;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(setterPrefix = "set")
@Entity
@Table(name = "comment")
public final class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "body")
    private String body;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private ArticleEntity article;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public static CommentEntity from(final Comment comment) {
        return CommentEntity.builder()
                .setId(comment.id())
                .setBody(comment.body())
                .setAuthor(UserEntity.from(comment.authorId()))
                .setArticle(ArticleEntity.from(comment.articleId()))
                .setCreatedAt(comment.createdAt())
                .setUpdatedAt(comment.updatedAt())
                .build();
    }

    @SuppressWarnings("unchecked")
    public <T> T into(final Class<T> target) {
        if (target == Comment.class) {
            return (T) intoCommentDomain(this);
        }

        throw new IllegalArgumentException("Cast to " + target + " is still not supported yet");
    }

    private Comment intoCommentDomain(final CommentEntity commentEntity) {
        return Comment.builder()
                .setId(commentEntity.id)
                .setBody(commentEntity.body)
                .setAuthorId(commentEntity.author.getId())
                .setArticleId(commentEntity.article.getId())
                .setCreatedAt(commentEntity.createdAt)
                .setUpdatedAt(commentEntity.updatedAt)
                .build();
    }
}
