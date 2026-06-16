package com.leviis.realworldexample.user.application.command.handler;

import com.leviis.realworldexample.user.application.command.UpdateUserCommand;
import com.leviis.realworldexample.user.application.port.inbound.UpdateUserUseCase;
import com.leviis.realworldexample.user.application.port.outbound.PasswordService;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateUserHandler implements UpdateUserUseCase {
    private final UserCommandRepository userCommandRepository;
    private final PasswordService passwordService;

    @Override
    public User execute(final UpdateUserCommand command) {
        return userCommandRepository.updateById(command.id(), getUpdatedUser(command));
    }

    private User getUpdatedUser(final UpdateUserCommand command) {
        final String hashedPassword = Optional.ofNullable(command.password())
                .map(passwordService::hashPassword)
                .orElse(null);

        return command.intoUserDomain(hashedPassword);
    }
}
