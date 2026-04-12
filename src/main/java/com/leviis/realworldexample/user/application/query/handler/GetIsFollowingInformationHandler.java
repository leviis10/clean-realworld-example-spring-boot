package com.leviis.realworldexample.user.application.query.handler;

import com.leviis.realworldexample.user.application.port.inbound.GetIsFollowingInformationUseCase;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.application.query.GetIsFollowingInformationQuery;

public final class GetIsFollowingInformationHandler implements GetIsFollowingInformationUseCase {
    private final UserQueryRepository userQueryRepository;

    public GetIsFollowingInformationHandler(final UserQueryRepository userQueryRepository) {
        this.userQueryRepository = userQueryRepository;
    }

    @Override
    public boolean execute(final GetIsFollowingInformationQuery query) {
        if (query.followerId() == null) {
            return false;
        }
        return userQueryRepository.getIsFollowing(query.followerId(), query.followingId());
    }
}
