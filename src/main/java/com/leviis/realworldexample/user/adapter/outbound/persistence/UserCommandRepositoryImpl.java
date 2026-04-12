package com.leviis.realworldexample.user.adapter.outbound.persistence;

import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowEntity;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowId;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.JpaFollowRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.JpaUserRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserCommandRepositoryImpl implements UserCommandRepository {
    private final JpaUserRepository jpaUserRepository;
    private final JpaFollowRepository jpaFollowRepository;

    @Override
    public User save(final User user) {
        var createdUser = jpaUserRepository.save(UserEntity.from(user));
        return createdUser.intoDomain();
    }

    @Override
    public User updateById(final Long id, final User updatedUserData) {
        var foundUser = jpaUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        updateUserEntity(foundUser, updatedUserData);
        var updatedUser = jpaUserRepository.save(foundUser);
        return updatedUser.intoDomain();
    }

    @Override
    public boolean followUser(final User follower, final User following) {
        jpaFollowRepository.save(FollowEntity.from(UserEntity.from(follower), UserEntity.from(following)));
        return true;
    }

    @Override
    public boolean unfollowUser(final Long followerId, final Long followingId) {
        jpaFollowRepository.deleteById(FollowId.builder()
                .followerId(followerId)
                .followingId(followingId)
                .build());
        return true;
    }

    private void updateUserEntity(final UserEntity currentUser, final User updatedUser) {
        updateEmail(currentUser, updatedUser);
        updateUsername(currentUser, updatedUser);
        updatePassword(currentUser, updatedUser);
        updateImage(currentUser, updatedUser);
        updateBio(currentUser, updatedUser);
    }

    private void updateBio(final UserEntity currentUser, final User updatedUser) {
        if (updatedUser.bio() != null) {
            currentUser.setBio(updatedUser.bio());
        }
    }

    private void updateImage(final UserEntity currentUser, final User updatedUser) {
        if (updatedUser.image() != null) {
            currentUser.setImage(updatedUser.image());
        }
    }

    private void updatePassword(final UserEntity currentUser, final User updatedUser) {
        if (updatedUser.password() != null) {
            currentUser.setPassword(updatedUser.password());
        }
    }

    private void updateUsername(final UserEntity currentUser, final User updatedUser) {
        if (updatedUser.username() != null) {
            currentUser.setUsername(updatedUser.username());
        }
    }

    private void updateEmail(final UserEntity currentUser, final User updatedUser) {
        if (updatedUser.email() != null) {
            currentUser.setEmail(updatedUser.email().value());
        }
    }
}
