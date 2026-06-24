package com.leviis.realworldexample.user.application.query.handler;

import com.leviis.realworldexample.user.application.exceptions.UserNotFoundException;
import com.leviis.realworldexample.user.application.port.inbound.GetUserProfileUseCase;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.application.query.GetUserProfileQuery;
import com.leviis.realworldexample.user.application.readmodel.UserWithFollowStatus;
import com.leviis.realworldexample.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GetUserProfileHandler implements GetUserProfileUseCase {
    private final UserQueryRepository userQueryRepository;

    @Override
    public UserWithFollowStatus execute(final GetUserProfileQuery query) {
        final User foundUser = userQueryRepository
                .findByUsername(query.username())
                .orElseThrow(() -> new UserNotFoundException(query.username()));
        final Optional<Long> followerId = Optional.ofNullable(query.user()).map(User::id);
        final boolean isFollowing = followerId
                .filter(fid -> userQueryRepository.getIsFollowing(fid, foundUser.id()))
                .isPresent();
        return UserWithFollowStatus.from(foundUser, isFollowing);
    }
}
