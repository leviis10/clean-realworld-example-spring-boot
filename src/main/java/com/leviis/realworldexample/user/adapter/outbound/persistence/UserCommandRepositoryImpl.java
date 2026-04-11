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

    @Override
    public User updateById(final Long id, final User updatedUserData) {
        var foundUser = jpaUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        updateUserEntity(foundUser, updatedUserData);
        var updatedUser = jpaUserRepository.save(foundUser);
        return updatedUser.intoDomain();
    }

    private void updateUserEntity(final UserEntity currentUser, final User updatedUser) {
        updateEmail(currentUser, updatedUser);
        updateUsername(currentUser, updatedUser);
        updatePassword(currentUser, updatedUser);
        updateImage(currentUser, updatedUser);
        updateBio(currentUser, updatedUser);
    }

    private void updateBio(final UserEntity currentUser, final User updatedUser) {
        if (updatedUser.getBio() != null) {
            currentUser.setBio(updatedUser.getBio());
        }
    }

    private void updateImage(final UserEntity currentUser, final User updatedUser) {
        if (updatedUser.getImage() != null) {
            currentUser.setImage(updatedUser.getImage());
        }
    }

    private void updatePassword(final UserEntity currentUser, final User updatedUser) {
        if (updatedUser.getPassword() != null) {
            currentUser.setPassword(updatedUser.getPassword());
        }
    }

    private void updateUsername(final UserEntity currentUser, final User updatedUser) {
        if (updatedUser.getUsername() != null) {
            currentUser.setUsername(updatedUser.getUsername());
        }
    }

    private void updateEmail(final UserEntity currentUser, final User updatedUser) {
        if (updatedUser.getEmail() != null) {
            currentUser.setEmail(updatedUser.getEmail());
        }
    }
}
