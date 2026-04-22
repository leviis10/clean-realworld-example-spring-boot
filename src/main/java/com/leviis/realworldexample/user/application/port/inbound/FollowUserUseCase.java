package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.command.FollowUserCommand;

public interface FollowUserUseCase {
    boolean execute(FollowUserCommand command);
}
