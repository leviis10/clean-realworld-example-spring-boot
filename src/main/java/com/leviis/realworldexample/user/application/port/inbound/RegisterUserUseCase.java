package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.command.RegisterUserCommand;
import com.leviis.realworldexample.user.domain.User;

public interface RegisterUserUseCase {
    User execute(RegisterUserCommand command);
}
