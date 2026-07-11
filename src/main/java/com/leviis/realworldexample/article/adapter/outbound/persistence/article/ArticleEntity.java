package com.leviis.realworldexample.article.adapter.outbound.persistence.article;

import com.leviis.realworldexample.article.adapter.outbound.persistence.articletag.ArticleTagEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.comment.CommentEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle.UserFavoriteArticleEntity;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "article")
public final class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "slug_id", nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID slugId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "body", nullable = false)
    private String body;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "article")
    private List<UserFavoriteArticleEntity> favoritesBy;

    @OneToMany(mappedBy = "article")
    private List<ArticleTagEntity> tags;

    @OneToMany(mappedBy = "article")
    private List<CommentEntity> comments;

    public static @NonNull ArticleEntity from(@NonNull final Article article, @Nullable final Map<Long, Tag> tagMap) {
        Objects.requireNonNull(article);

        return ArticleEntity.builder()
                .id(article.id())
                .slug(article.slug().value())
                .slugId(article.slug().id())
                .title(article.title())
                .description(article.description())
                .body(article.body())
                .author(UserEntity.builder().id(article.authorId()).build())
                .createdAt(article.createdAt())
                .updatedAt(article.updatedAt())
                .tags(getTags(article, tagMap))
                .build();
    }

    public static @NonNull ArticleEntity from(@NonNull final Article article) {
        return from(article, null);
    }

    public static ArticleEntity from(final Long articleId) {
        return ArticleEntity.builder().id(articleId).build();
    }

    @SuppressWarnings("unchecked")
    public <T> T into(final Class<T> target) {
        if (target == Article.class) {
            return (T) this.intoArticleDomain();
        }

        throw new IllegalArgumentException("Cast to " + target + " is not supported");
    }

    private static List<ArticleTagEntity> getTags(final Article article, @Nullable final Map<Long, Tag> tagMap) {
        return Optional.ofNullable(tagMap)
                .map(tm -> ArticleTagEntity.from(article.tagIds(), tm))
                .orElse(List.of());
    }

    private Article intoArticleDomain() {
        final List<Long> tagIds =
                this.tags.stream().map(tag -> tag.getId().getTagId()).toList();

        return Article.builder()
                .setId(this.id)
                .setSlug(new Slug(this.slug, this.slugId))
                .setTitle(this.title)
                .setDescription(this.description)
                .setBody(this.body)
                .setAuthorId(this.author.getId())
                .setTagIds(tagIds)
                .setCreatedAt(this.createdAt)
                .setUpdatedAt(this.updatedAt)
                .build();
    }
}
