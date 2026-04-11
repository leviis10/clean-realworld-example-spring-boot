package com.leviis.realworldexample.user.adapter;

import com.leviis.realworldexample.user.application.command.RegisterUserHandler;
import com.leviis.realworldexample.user.application.command.handler.UpdateUserHandler;
import com.leviis.realworldexample.user.application.port.inbound.RegisterUserUseCase;
import com.leviis.realworldexample.user.application.port.inbound.UpdateUserUseCase;
import com.leviis.realworldexample.user.application.port.inbound.UserLoginUseCase;
import com.leviis.realworldexample.user.application.port.outbound.PasswordService;
import com.leviis.realworldexample.user.application.port.outbound.TokenService;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.application.query.UserLoginHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AdapterConfig {
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;
    private final PasswordService passwordService;
    private final TokenService tokenService;

    @Bean
    public RegisterUserUseCase registerUserUseCase() {
        return new RegisterUserHandler(userCommandRepository, userQueryRepository, passwordService, tokenService);
    }

    @Bean
    public UserLoginUseCase userLoginUseCase() {
        return new UserLoginHandler(userQueryRepository, passwordService, tokenService);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase() {
        return new UpdateUserHandler(userCommandRepository, passwordService);
    }
}
