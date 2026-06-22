package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.command.FollowUserCommand;
import com.leviis.realworldexample.user.application.readmodel.UserWithFollowStatus;

@FunctionalInterface
public interface FollowUserUseCase {
    UserWithFollowStatus execute(FollowUserCommand command);
}
