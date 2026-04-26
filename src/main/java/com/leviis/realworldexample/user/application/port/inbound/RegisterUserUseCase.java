package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.command.RegisterUserCommand;
import com.leviis.realworldexample.user.application.command.UserWithToken;

public interface RegisterUserUseCase {
    UserWithToken execute(RegisterUserCommand command);
}
