package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.query.UserLoginQuery;
import com.leviis.realworldexample.user.application.readmodel.UserWithToken;

@FunctionalInterface
public interface UserLoginUseCase {
    UserWithToken execute(UserLoginQuery query);
}
