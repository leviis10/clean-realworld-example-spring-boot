package com.leviis.realworldexample.user.adapter.outbound.persistence;

import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowEntity;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowId;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.JpaFollowRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.JpaUserRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.application.exceptions.SelfFollowException;
import com.leviis.realworldexample.user.application.exceptions.UserNotFoundException;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.domain.User;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserCommandRepositoryImpl implements UserCommandRepository {
    private final JpaUserRepository jpaUserRepository;
    private final JpaFollowRepository jpaFollowRepository;

    @Override
    public User save(final User user) {
        final UserEntity createdUser = jpaUserRepository.save(UserEntity.from(user));

        return createdUser.intoDomain();
    }

    @Override
    public User updateById(final Long id, final User updatedUserData) {
        final UserEntity foundUser = jpaUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        updateUserEntity(foundUser, updatedUserData);
        final UserEntity updatedUser = jpaUserRepository.save(foundUser);

        return updatedUser.intoDomain();
    }

    @Override
    public void followUser(@NonNull final User follower, @NonNull final User following) {
        if (Objects.requireNonNull(follower.id()).equals(following.id())) {
            throw new SelfFollowException();
        }

        jpaFollowRepository.save(FollowEntity.from(UserEntity.from(follower), UserEntity.from(following)));
    }

    @Override
    public void unfollowUser(@NonNull final Long followerId, @NonNull final Long followingId) {
        if (followerId.equals(followingId)) {
            throw new IllegalArgumentException("Follower and following id cannot be same");
        }

        jpaFollowRepository.deleteById(FollowId.builder()
                .followerId(followerId)
                .followingId(followingId)
                .build());
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
        currentUser.setUsername(updatedUser.username());
    }

    private void updateEmail(final UserEntity currentUser, final User updatedUser) {
        currentUser.setEmail(updatedUser.email().value());
    }
}
