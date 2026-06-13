package com.leviis.realworldexample.user.application.command.handler;

import com.leviis.realworldexample.infrastructure.exceptions.DuplicateResourceException;
import com.leviis.realworldexample.infrastructure.exceptions.ProblemError;
import com.leviis.realworldexample.user.application.command.RegisterUserCommand;
import com.leviis.realworldexample.user.application.command.UserWithToken;
import com.leviis.realworldexample.user.application.port.inbound.RegisterUserUseCase;
import com.leviis.realworldexample.user.application.port.outbound.PasswordService;
import com.leviis.realworldexample.user.application.port.outbound.TokenService;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class RegisterUserHandler implements RegisterUserUseCase {
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;
    private final PasswordService passwordService;
    private final TokenService tokenService;

    @Override
    public UserWithToken execute(final RegisterUserCommand command) {
        validateUserExists(command);

        final String hashedPassword = passwordService.hashPassword(command.password());
        final User createdUser = userCommandRepository.save(User.builder()
                .setEmail(command.email())
                .setUsername(command.username())
                .setPassword(hashedPassword)
                .build());

        final String token = tokenService.generateToken(createdUser);
        return UserWithToken.from(createdUser, token);
    }

    private void validateUserExists(final RegisterUserCommand command) {
        final Optional<User> foundUserByEmail = userQueryRepository.findByEmail(command.email());
        final Optional<User> foundUserByUsername = userQueryRepository.findByUsername(command.username());
        final List<ProblemError> errors = new ArrayList<>();

        if (foundUserByEmail.isPresent()) {
            errors.add(ProblemError.builder()
                    .setField("email")
                    .setCode("USER_ALREADY_EXISTS")
                    .setMessage("Email is already registered")
                    .setRejectedValue(command.email().value())
                    .build());
        }

        if (foundUserByUsername.isPresent()) {
            errors.add(ProblemError.builder()
                    .setField("username")
                    .setCode("USER_ALREADY_EXISTS")
                    .setMessage("Username is already registered")
                    .setRejectedValue(command.username())
                    .build());
        }

        if (!errors.isEmpty()) {
            throw new DuplicateResourceException("User is already registered", errors);
        }
    }
}
