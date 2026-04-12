package com.leviis.realworldexample.user.application.query.handler;

import com.leviis.realworldexample.user.application.port.inbound.UserLoginUseCase;
import com.leviis.realworldexample.user.application.port.outbound.PasswordService;
import com.leviis.realworldexample.user.application.port.outbound.TokenService;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.application.query.UserLoginQuery;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;

public final class UserLoginHandler implements UserLoginUseCase {
    private final UserQueryRepository userQueryRepository;
    private final PasswordService passwordService;
    private final TokenService tokenService;

    public UserLoginHandler(
            final UserQueryRepository userQueryRepository,
            final PasswordService passwordService,
            final TokenService tokenService) {
        this.userQueryRepository = userQueryRepository;
        this.passwordService = passwordService;
        this.tokenService = tokenService;
    }

    @Override
    public User execute(final UserLoginQuery query) {
        var foundUser = userQueryRepository
                .findByEmail(new Email(query.email()))
                .orElseThrow(() -> new RuntimeException("Unregistered email address"));

        var isCorrectPassword = passwordService.compare(query.password(), foundUser.password());
        if (!isCorrectPassword) {
            throw new RuntimeException("Incorrect Password");
        }

        var token = tokenService.generateToken(foundUser);
        return User.from(foundUser).setToken(token).build();
    }
}
