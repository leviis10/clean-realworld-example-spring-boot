package com.leviis.realworldexample.user.application.query.handler;

import com.leviis.realworldexample.user.application.exceptions.IncorrectCredentialsException;
import com.leviis.realworldexample.user.application.port.inbound.UserLoginUseCase;
import com.leviis.realworldexample.user.application.port.outbound.PasswordService;
import com.leviis.realworldexample.user.application.port.outbound.TokenService;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.application.query.UserLoginQuery;
import com.leviis.realworldexample.user.application.readmodel.UserWithToken;
import com.leviis.realworldexample.user.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UserLoginHandler implements UserLoginUseCase {
    private final UserQueryRepository userQueryRepository;
    private final PasswordService passwordService;
    private final TokenService tokenService;

    @Override
    public UserWithToken execute(final UserLoginQuery query) {
        final User foundUser =
                userQueryRepository.findByEmail(query.email()).orElseThrow(IncorrectCredentialsException::new);

        final boolean isCorrectPassword = passwordService.compare(query.password(), foundUser.password());
        if (!isCorrectPassword) {
            throw new IncorrectCredentialsException();
        }

        final String token = tokenService.generateToken(foundUser);
        return UserWithToken.from(foundUser, token);
    }
}
