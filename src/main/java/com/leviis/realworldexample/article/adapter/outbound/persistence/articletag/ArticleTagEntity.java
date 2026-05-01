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
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
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

    public static ArticleTagEntity from(@Nullable final ArticleEntity articleEntity, @Nullable final Tag tag) {
        return ArticleTagEntity.builder()
                .id(ArticleTagId.from(getArticleId(articleEntity), getTagId(tag)))
                .article(articleEntity)
                .tag(TagEntity.from(tag))
                .build();
    }

    private static @Nullable Long getTagId(@Nullable final Tag tag) {
        return Optional.ofNullable(tag).map(Tag::id).orElse(null);
    }

    private static @Nullable Long getArticleId(@Nullable final ArticleEntity articleEntity) {
        return Optional.ofNullable(articleEntity).map(ArticleEntity::getId).orElse(null);
    }

    public static ArticleTagEntity from(final Long tagId, @Nullable final Map<Long, Tag> tagMap) {
        return from(null, getTag(tagId, tagMap));
    }

    private static @Nullable Tag getTag(final Long tagId, @Nullable final Map<Long, Tag> tagMap) {
        return Optional.ofNullable(tagMap)
                .map(map -> map.getOrDefault(tagId, null))
                .orElse(null);
    }
}
