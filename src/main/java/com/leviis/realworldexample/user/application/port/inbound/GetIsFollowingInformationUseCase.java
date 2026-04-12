package com.leviis.realworldexample.user.application.port.inbound;

import com.leviis.realworldexample.user.application.query.GetIsFollowingInformationQuery;

public interface GetIsFollowingInformationUseCase {
    boolean execute(GetIsFollowingInformationQuery query);
}
