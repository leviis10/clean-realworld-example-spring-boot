package com.leviis.realworldexample.user.application.query.handler;

import com.leviis.realworldexample.user.application.port.inbound.GetUserProfileUseCase;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.application.query.GetUserProfileQuery;
import com.leviis.realworldexample.user.domain.User;

public final class GetUserProfileHandler implements GetUserProfileUseCase {
    private final UserQueryRepository userQueryRepository;

    public GetUserProfileHandler(final UserQueryRepository userQueryRepository) {
        this.userQueryRepository = userQueryRepository;
    }

    @Override
    public User execute(final GetUserProfileQuery query) {
        return userQueryRepository
                .findByUsername(query.username())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
