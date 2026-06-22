package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.query.GetUserProfileQuery;
import com.leviis.realworldexample.user.application.readmodel.UserWithFollowStatus;

@FunctionalInterface
public interface GetUserProfileUseCase {
    UserWithFollowStatus execute(GetUserProfileQuery query);
}
