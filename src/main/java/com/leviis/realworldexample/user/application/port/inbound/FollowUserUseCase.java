package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.domain.User;

public interface FollowUserUseCase {
    boolean execute(User follower, User following);
}
