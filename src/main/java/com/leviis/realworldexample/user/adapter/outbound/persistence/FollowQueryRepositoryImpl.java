package com.leviis.realworldexample.user.adapter.outbound.persistence;

import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowEntity;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowId;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.JpaFollowRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.application.port.outbound.FollowQueryRepository;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class FollowQueryRepositoryImpl implements FollowQueryRepository {
    private final JpaFollowRepository jpaFollowRepository;

    @Override
    public List<Long> findAllFollowingIdByFollowerId(final Long followerId) {
        final List<FollowEntity> result = jpaFollowRepository.findAllByFollower(
                UserEntity.builder().id(followerId).build());

        return result.stream()
                .map(followEntity -> followEntity.getId().getFollowingId())
                .toList();
    }

    @Override
    public boolean findIsFollowing(@Nullable final User follower, @Nullable final User following) {
        if (follower == null || following == null) {
            return false;
        }

        return jpaFollowRepository
                .findById(FollowId.builder()
                        .followerId(follower.id())
                        .followingId(following.id())
                        .build())
                .isPresent();
    }

    @Override
    public List<Long> findByFollowerAndFollowingIdIn(final Long followerId, final Set<Long> followingIds) {
        final List<UserEntity> followingEntities =
                followingIds.stream().map(UserEntity::from).toList();
        final List<FollowEntity> foundFollows =
                jpaFollowRepository.findByFollowerAndFollowingIn(UserEntity.from(followerId), followingEntities);
        return foundFollows.stream()
                .map(followEntity -> followEntity.getId().getFollowingId())
                .toList();
    }
}
