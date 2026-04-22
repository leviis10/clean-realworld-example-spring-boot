package com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_favorite_article")
public class UserFavoriteArticleEntity {
    @EmbeddedId
    private UserFavoriteArticleId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("articleId")
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}
