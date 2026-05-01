package com.leviis.realworldexample.user.application.command.handler;

import com.leviis.realworldexample.user.application.command.FollowUserCommand;
import com.leviis.realworldexample.user.application.port.inbound.FollowUserUseCase;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.User;
import com.leviis.realworldexample.user.domain.exceptions.AlreadyFollowException;
import com.leviis.realworldexample.user.domain.exceptions.SelfFollowException;

public final class FollowUserHandler implements FollowUserUseCase {
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;

    public FollowUserHandler(
            final UserCommandRepository userCommandRepository, final UserQueryRepository userQueryRepository) {
        this.userCommandRepository = userCommandRepository;
        this.userQueryRepository = userQueryRepository;
    }

    @Override
    public boolean execute(final FollowUserCommand command) {
        final User follower = command.follower();
        final User following = command.following();

        if (follower.id().equals(following.id())) {
            throw new SelfFollowException();
        }

        final boolean isAlreadyFollowing = userQueryRepository.getIsFollowing(follower.id(), following.id());
        if (isAlreadyFollowing) {
            throw new AlreadyFollowException();
        }

        return userCommandRepository.followUser(follower, following);
    }
}
