package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.command.UnfollowUserCommand;
import com.leviis.realworldexample.user.application.readmodel.UserWithFollowStatus;

@FunctionalInterface
public interface UnfollowUserUseCase {
    UserWithFollowStatus execute(UnfollowUserCommand command);
}
