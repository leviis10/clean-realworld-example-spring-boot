package com.leviis.realworldexample.user.adapter.outbound.persistence;

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
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserQueryRepositoryImpl implements UserQueryRepository {
    private final JpaUserRepository jpaUserRepository;
    private final JpaFollowRepository jpaFollowRepository;

    @Override
    public Optional<User> findByEmail(final Email email) {
        var foundUser = jpaUserRepository.findByEmail(email.value());
        return foundUser.map(UserEntity::intoDomain);
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        var foundUser = jpaUserRepository.findByUsername(username);
        return foundUser.map(UserEntity::intoDomain);
    }

    @Override
    public Optional<User> findById(final Long id) {
        var foundUser = jpaUserRepository.findById(id);
        return foundUser.map(UserEntity::intoDomain);
    }

    @Override
    public boolean getIsFollowing(final Long followerId, final Long followingId) {
        var data = jpaFollowRepository.findById(FollowId.builder()
                .followerId(followerId)
                .followingId(followingId)
                .build());
        return data.isPresent();
    }

    @Override
    public List<User> findByIds(final Set<Long> ids) {
        var foundUsers = jpaUserRepository.findAllById(ids);
        return foundUsers.stream().map(UserEntity::intoDomain).toList();
    }

    @Override
    public List<Long> findIsFollowingIn(final User follower, final List<User> followings) {
        if (follower == null) {
            return List.of();
        }

        var followingEntity = followings.stream().map(UserEntity::from).toList();
        var followData = jpaFollowRepository.findByFollowerAndFollowingIn(UserEntity.from(follower), followingEntity);
        return followData.stream().map(data -> data.getId().getFollowingId()).toList();
    }
}
