package com.leviis.realworldexample.user.adapter.outbound.persistence;

import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowEntity;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowId;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.JpaFollowRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.JpaUserRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserQueryRepositoryImpl implements UserQueryRepository {
    private final JpaUserRepository jpaUserRepository;
    private final JpaFollowRepository jpaFollowRepository;

    @Override
    public Optional<User> findByEmail(final Email email) {
        final Optional<UserEntity> foundUser = jpaUserRepository.findByEmail(email.value());

        return foundUser.map(UserEntity::intoDomain);
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        final Optional<UserEntity> foundUser = jpaUserRepository.findByUsername(username);

        return foundUser.map(UserEntity::intoDomain);
    }

    @Override
    public Optional<User> findById(final Long id) {
        final Optional<UserEntity> foundUser = jpaUserRepository.findById(id);

        return foundUser.map(UserEntity::intoDomain);
    }

    @Override
    public boolean getIsFollowing(final Long followerId, final Long followingId) {
        final Optional<FollowEntity> data = jpaFollowRepository.findById(FollowId.builder()
                .followerId(followerId)
                .followingId(followingId)
                .build());

        return data.isPresent();
    }

    @Override
    public List<User> findByIds(final Set<Long> ids) {
        final List<UserEntity> foundUsers = jpaUserRepository.findAllById(ids);

        return foundUsers.stream().map(UserEntity::intoDomain).toList();
    }

    @Override
    public List<Long> findIsFollowingIn(@Nullable final User follower, final List<User> followings) {
        return Optional.ofNullable(follower)
                .map(follow -> {
                    final List<UserEntity> followingEntity =
                            followings.stream().map(UserEntity::from).toList();
                    final List<FollowEntity> followData =
                            jpaFollowRepository.findByFollowerAndFollowingIn(UserEntity.from(follow), followingEntity);

                    return followData.stream()
                            .map(data -> data.getId().getFollowingId())
                            .toList();
                })
                .orElse(List.of());
    }
}
