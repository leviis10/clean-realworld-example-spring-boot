package com.leviis.realworldexample.user.application.command.handler;

import com.leviis.realworldexample.user.application.command.UnfollowUserCommand;
import com.leviis.realworldexample.user.application.exceptions.AlreadyUnfollowException;
import com.leviis.realworldexample.user.application.exceptions.SelfUnfollowException;
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
    public boolean execute(final UnfollowUserCommand command) {
        if (command.followerId().equals(command.followingId())) {
            throw new SelfUnfollowException();
        }

        final boolean getIsFollowing = userQueryRepository.getIsFollowing(command.followerId(), command.followingId());
        if (!getIsFollowing) {
            throw new AlreadyUnfollowException();
        }

        return userCommandRepository.unfollowUser(command.followerId(), command.followingId());
    }
}
