package com.leviis.realworldexample.user.application.command.handler;

import com.leviis.realworldexample.user.application.command.RegisterUserCommand;
import com.leviis.realworldexample.user.application.command.UserWithToken;
import com.leviis.realworldexample.user.application.port.inbound.RegisterUserUseCase;
import com.leviis.realworldexample.user.application.port.outbound.PasswordService;
import com.leviis.realworldexample.user.application.port.outbound.TokenService;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.RawPassword;
import com.leviis.realworldexample.user.domain.User;

public final class RegisterUserHandler implements RegisterUserUseCase {
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;
    private final PasswordService passwordService;
    private final TokenService tokenService;

    public RegisterUserHandler(
            final UserCommandRepository userCommandRepository,
            final UserQueryRepository userQueryRepository,
            final PasswordService passwordService,
            final TokenService tokenService) {
        this.userCommandRepository = userCommandRepository;
        this.userQueryRepository = userQueryRepository;
        this.passwordService = passwordService;
        this.tokenService = tokenService;
    }

    @Override
    public UserWithToken execute(final RegisterUserCommand command) {
        Email email = new Email(command.email());
        RawPassword rawPassword = new RawPassword(command.password());

        validateUserExists(command, email);

        var hashedPassword = passwordService.hashPassword(rawPassword);
        var createdUser = userCommandRepository.save(User.builder()
                .setEmail(email)
                .setUsername(command.username())
                .setPassword(hashedPassword)
                .build());

        var token = tokenService.generateToken(createdUser);
        return UserWithToken.from(createdUser, token);
    }

    private void validateUserExists(final RegisterUserCommand command, final Email email) {
        var foundUserByEmail = userQueryRepository.findByEmail(email);
        if (foundUserByEmail.isPresent()) {
            throw new IllegalStateException("Email is already registered");
        }
        var foundUserByUsername = userQueryRepository.findByUsername(command.username());
        if (foundUserByUsername.isPresent()) {
            throw new IllegalStateException("Username is already registered");
        }
    }
}
