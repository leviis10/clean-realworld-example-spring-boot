package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.command.RegisterUserCommand;
import com.leviis.realworldexample.user.application.command.UserWithToken;

@FunctionalInterface
public interface RegisterUserUseCase {
    UserWithToken execute(RegisterUserCommand command);
}
