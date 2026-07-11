package com.leviis.realworldexample.article.adapter.outbound.persistence.articletag;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.tag.adapter.outbound.persistence.tag.TagEntity;
import com.leviis.realworldexample.tag.domain.Tag;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "article_tag")
public class ArticleTagEntity {
    @EmbeddedId
    private ArticleTagId id;

    @ManyToOne
    @MapsId("articleId")
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private TagEntity tag;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    public static @NonNull ArticleTagEntity from(@Nullable final ArticleEntity articleEntity, @Nullable final Tag tag) {
        if (articleEntity == null && tag == null) {
            throw new IllegalArgumentException("articleEntity and tag cannot be null at the same time");
        }

        final Long articleId =
                Optional.ofNullable(articleEntity).map(ArticleEntity::getId).orElse(null);
        final Long tagId = Optional.ofNullable(tag).map(Tag::id).orElse(null);

        return ArticleTagEntity.builder()
                .id(ArticleTagId.from(articleId, tagId))
                .article(articleEntity)
                .tag(TagEntity.from(tag))
                .build();
    }

    public static @NonNull ArticleTagEntity from(@NonNull final Long tagId, @NonNull final Map<Long, Tag> tagMap) {
        final Tag tag = getTag(tagId, tagMap).orElse(null);

        return from(null, tag);
    }

    public static @NonNull List<ArticleTagEntity> from(
            @NonNull final List<Long> tagIds, @NonNull final Map<Long, Tag> tagMap) {
        return tagIds.stream().map(tagId -> from(tagId, tagMap)).toList();
    }

    private static Optional<Tag> getTag(final Long tagId, @Nullable final Map<Long, Tag> tagMap) {
        return Optional.ofNullable(tagMap).map(map -> map.getOrDefault(tagId, null));
    }
}
