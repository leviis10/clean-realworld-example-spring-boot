package com.leviis.realworldexample.user.adapter.outbound.persistence.follow;

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
@Table(name = "follow")
public class FollowEntity {
    @EmbeddedId
    private FollowId id;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    private UserEntity follower;

    @ManyToOne
    @MapsId("followingId")
    @JoinColumn(name = "following_id")
    private UserEntity following;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}
