package com.leviis.realworldexample.user.adapter.outbound.persistence.user;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.userfavoritearticle.UserFavoriteArticleEntity;
import com.leviis.realworldexample.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;
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
@Table(name = "user")
public final class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "bio")
    private String bio;

    @Column(name = "image")
    private String image;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<UserFavoriteArticleEntity> favoriteArticles;

    @OneToMany(mappedBy = "author")
    private List<ArticleEntity> articles;

    public static UserEntity from(final User user) {
        return UserEntity.builder()
                .id(user.id())
                .email(user.email().value())
                .username(user.username())
                .password(user.password())
                .bio(user.bio())
                .image(user.image())
                .build();
    }

    public User intoDomain() {
        return User.builder()
                .setId(this.id)
                .setEmail(this.email)
                .setUsername(this.username)
                .setBio(this.bio)
                .setImage(this.image)
                .setPassword(this.password)
                .build();
    }
}
