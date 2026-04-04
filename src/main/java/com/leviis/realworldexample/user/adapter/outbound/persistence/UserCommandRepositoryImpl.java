package com.leviis.realworldexample.user.adapter.outbound.persistence;

import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserCommandRepositoryImpl implements UserCommandRepository {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(final User user) {
        var createdUser = jpaUserRepository.save(UserEntity.from(user));
        return createdUser.intoDomain();
    }
}
