package com.leviis.realworldexample.user.application.command.handler;

import com.leviis.realworldexample.user.application.command.UpdateUserCommand;
import com.leviis.realworldexample.user.application.port.inbound.UpdateUserUseCase;
import com.leviis.realworldexample.user.application.port.outbound.PasswordService;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.domain.Password;
import com.leviis.realworldexample.user.domain.User;

public final class UpdateUserHandler implements UpdateUserUseCase {
    private final UserCommandRepository userCommandRepository;
    private final PasswordService passwordService;

    public UpdateUserHandler(final UserCommandRepository userCommandRepository, final PasswordService passwordService) {
        this.userCommandRepository = userCommandRepository;
        this.passwordService = passwordService;
    }

    @Override
    public User execute(final UpdateUserCommand command) {
        if (command.getPassword() != null) {
            var hashedPassword = passwordService.hashPassword(new Password(command.getPassword()));
            command.setHashedPassword(hashedPassword);
        }

        return userCommandRepository.updateById(command.getId(), command.intoUserDomain());
    }
}
