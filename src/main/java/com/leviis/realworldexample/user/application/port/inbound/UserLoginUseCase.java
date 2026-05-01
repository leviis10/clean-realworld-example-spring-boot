package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.command.UserWithToken;
import com.leviis.realworldexample.user.application.query.UserLoginQuery;

@FunctionalInterface
public interface UserLoginUseCase {
    UserWithToken execute(UserLoginQuery query);
}
