package com.leviis.realworldexample.user.adapter.outbound.persistence;

import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserQueryRepositoryImpl implements UserQueryRepository {
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
}
