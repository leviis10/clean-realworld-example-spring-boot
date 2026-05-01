package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.query.GetUserProfileQuery;
import com.leviis.realworldexample.user.domain.User;

@FunctionalInterface
public interface GetUserProfileUseCase {
    User execute(GetUserProfileQuery query);
}
