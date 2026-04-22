package com.leviis.realworldexample.article.adapter.outbound.persistence.article;

import com.leviis.realworldexample.article.adapter.outbound.persistence.articletag.ArticleTagEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle.UserFavoriteArticleEntity;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
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
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private UUID slugId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

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

    public static ArticleEntity from(final Article article) {
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
                .build();
    }

    public Article intoArticleDomain() {
        var tagIds = this.tags.stream().map(tag -> tag.getId().getTagId()).toList();

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
