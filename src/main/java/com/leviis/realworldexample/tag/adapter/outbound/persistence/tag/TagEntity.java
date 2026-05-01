package com.leviis.realworldexample.tag.adapter.outbound.persistence.tag;

import com.leviis.realworldexample.article.adapter.outbound.persistence.articletag.ArticleTagEntity;
import com.leviis.realworldexample.tag.domain.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jspecify.annotations.Nullable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tag")
public final class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "tag")
    private List<ArticleTagEntity> articles;

    public static TagEntity from(@Nullable final Tag tag) {
        return TagEntity.builder().id(getId(tag)).name(getName(tag)).build();
    }

    private static @Nullable String getName(@Nullable final Tag tag) {
        return Optional.ofNullable(tag).map(Tag::name).orElse(null);
    }

    private static @Nullable Long getId(@Nullable final Tag tag) {
        return Optional.ofNullable(tag).map(Tag::id).orElse(null);
    }

    public Tag intoTagDomain() {
        return Tag.builder().setId(this.id).setName(this.name).build();
    }
}
