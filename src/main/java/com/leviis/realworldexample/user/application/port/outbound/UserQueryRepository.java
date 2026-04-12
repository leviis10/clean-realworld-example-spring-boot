package com.leviis.realworldexample.user.application.port.outbound;

import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.util.Optional;

public interface UserQueryRepository {
    Optional<User> findByEmail(Email email);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    boolean getIsFollowing(Long followerId, Long followingId);
}
