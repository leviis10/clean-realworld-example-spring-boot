package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.command.UserWithToken;
import com.leviis.realworldexample.user.application.query.UserLoginQuery;

public interface UserLoginUseCase {
    UserWithToken execute(UserLoginQuery query);
}
