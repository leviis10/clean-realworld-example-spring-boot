package com.leviis.realworldexample.user.application.command.handler;

import com.leviis.realworldexample.user.application.port.inbound.UnfollowUserUseCase;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;

public final class UnfollowUserHandler implements UnfollowUserUseCase {
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;

    public UnfollowUserHandler(
            final UserCommandRepository userCommandRepository, final UserQueryRepository userQueryRepository) {
        this.userCommandRepository = userCommandRepository;
        this.userQueryRepository = userQueryRepository;
    }

    @Override
    public boolean execute(final Long followerId, final Long followingId) {
        if (followerId.equals(followingId)) {
            throw new RuntimeException("Cannot self unfollow");
        }

        var getIsFollowing = userQueryRepository.getIsFollowing(followerId, followingId);
        if (!getIsFollowing) {
            throw new RuntimeException("Already Unfollowed");
        }

        return userCommandRepository.unfollowUser(followerId, followingId);
    }
}
