package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.command.UnfollowUserCommand;

public interface UnfollowUserUseCase {
    boolean execute(UnfollowUserCommand command);
}
