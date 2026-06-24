package com.leviis.realworldexample.user.application.command.handler;

import com.leviis.realworldexample.user.application.command.FollowUserCommand;
import com.leviis.realworldexample.user.application.exceptions.AlreadyFollowException;
import com.leviis.realworldexample.user.application.exceptions.SelfFollowException;
import com.leviis.realworldexample.user.application.exceptions.UserNotFoundException;
import com.leviis.realworldexample.user.application.port.inbound.FollowUserUseCase;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.application.readmodel.UserWithFollowStatus;
import com.leviis.realworldexample.user.domain.User;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

@RequiredArgsConstructor
public final class FollowUserHandler implements FollowUserUseCase {
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;

    @Override
    public UserWithFollowStatus execute(@NonNull final FollowUserCommand command) {
        Objects.requireNonNull(command);

        final User follower = command.follower();
        final Optional<User> followingOpt = userQueryRepository.findByUsername(command.followingUsername());
        if (followingOpt.isEmpty()) {
            throw new UserNotFoundException(command.followingUsername());
        }
        final User following = followingOpt.get();

        if (Objects.requireNonNull(follower.id()).equals(following.id())) {
            throw new SelfFollowException();
        }

        final boolean isAlreadyFollowing = userQueryRepository.getIsFollowing(follower.id(), following.id());
        if (isAlreadyFollowing) {
            throw new AlreadyFollowException();
        }

        userCommandRepository.followUser(follower, following);
        return UserWithFollowStatus.from(following, true);
    }
}
