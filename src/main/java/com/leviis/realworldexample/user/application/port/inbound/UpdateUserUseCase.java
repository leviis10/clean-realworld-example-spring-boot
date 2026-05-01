package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.command.UpdateUserCommand;
import com.leviis.realworldexample.user.domain.User;

@FunctionalInterface
public interface UpdateUserUseCase {
    User execute(UpdateUserCommand command);
}
