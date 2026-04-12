package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.query.GetUserProfileQuery;
import com.leviis.realworldexample.user.domain.User;

public interface GetUserProfileUseCase {
    User execute(GetUserProfileQuery query);
}
