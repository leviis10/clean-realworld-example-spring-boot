package com.leviis.realworldexample.article.adapter.outbound.persistence.articletag;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.tag.adapter.outbound.persistence.tag.TagEntity;
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
}
