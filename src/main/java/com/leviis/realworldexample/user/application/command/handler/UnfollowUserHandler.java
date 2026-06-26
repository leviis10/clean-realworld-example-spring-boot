package com.leviis.realworldexample.user.application.command.handler;

import com.leviis.realworldexample.user.application.command.UnfollowUserCommand;
import com.leviis.realworldexample.user.application.exceptions.AlreadyUnfollowException;
import com.leviis.realworldexample.user.application.exceptions.SelfUnfollowException;
import com.leviis.realworldexample.user.application.exceptions.UserNotFoundException;
import com.leviis.realworldexample.user.application.port.inbound.UnfollowUserUseCase;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.application.readmodel.UserWithFollowStatus;
import com.leviis.realworldexample.user.domain.User;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UnfollowUserHandler implements UnfollowUserUseCase {
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;

    @Override
    public UserWithFollowStatus execute(@NonNull final UnfollowUserCommand command) {
        Objects.requireNonNull(command);

        final User followingUser = userQueryRepository
                .findByUsername(command.followingUsername())
                .orElseThrow(() -> new UserNotFoundException(command.followingUsername()));
        if (command.followerId().equals(followingUser.id())) {
            throw new SelfUnfollowException();
        }

        final boolean getIsFollowing = userQueryRepository.getIsFollowing(command.followerId(), followingUser.id());
        if (!getIsFollowing) {
            throw new AlreadyUnfollowException();
        }

        userCommandRepository.unfollowUser(command.followerId(), followingUser.id());
        return UserWithFollowStatus.from(followingUser, false);
    }
}
