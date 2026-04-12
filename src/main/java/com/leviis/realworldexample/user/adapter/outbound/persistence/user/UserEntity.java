package com.leviis.realworldexample.user.adapter.outbound.persistence.user;

import com.leviis.realworldexample.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
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

    public static UserEntity from(final User user) {
        return UserEntity.builder()
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
