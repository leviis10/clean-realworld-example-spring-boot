package com.leviis.realworldexample.user.application.port.outbound;

import com.leviis.realworldexample.user.domain.User;

public interface UserCommandRepository {
    User save(User user);

    User updateById(Long id, User updatedUser);

    boolean followUser(User follower, User following);
}
