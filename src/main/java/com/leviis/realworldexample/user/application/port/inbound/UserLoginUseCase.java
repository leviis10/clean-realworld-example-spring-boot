package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.query.UserLoginQuery;
import com.leviis.realworldexample.user.domain.User;

public interface UserLoginUseCase {
    User execute(UserLoginQuery query);
}
