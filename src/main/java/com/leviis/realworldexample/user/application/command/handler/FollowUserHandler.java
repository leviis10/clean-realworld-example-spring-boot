package com.leviis.realworldexample.user.application.command.handler;

import com.leviis.realworldexample.user.application.port.inbound.FollowUserUseCase;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.User;

public final class FollowUserHandler implements FollowUserUseCase {
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;

    public FollowUserHandler(
            final UserCommandRepository userCommandRepository, final UserQueryRepository userQueryRepository) {
        this.userCommandRepository = userCommandRepository;
        this.userQueryRepository = userQueryRepository;
    }

    @Override
    public boolean execute(final User follower, final User following) {
        if (follower.id().equals(following.id())) {
            throw new RuntimeException("Cannot self yourself");
        }

        var isAlreadyFollowing = userQueryRepository.getIsFollowing(follower.id(), following.id());
        if (isAlreadyFollowing) {
            throw new RuntimeException("Already followed");
        }

        return userCommandRepository.followUser(follower, following);
    }
}
