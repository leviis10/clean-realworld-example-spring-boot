package com.leviis.realworldexample.user.adapter.inbound.http;

import com.leviis.realworldexample.infrastructure.UserContext;
import com.leviis.realworldexample.user.adapter.inbound.http.dto.request.UpdateUserRequest;
import com.leviis.realworldexample.user.adapter.inbound.http.dto.response.FollowUserResponse;
import com.leviis.realworldexample.user.adapter.inbound.http.dto.response.GetUserProfileResponse;
import com.leviis.realworldexample.user.adapter.inbound.http.dto.response.UnfollowResponse;
import com.leviis.realworldexample.user.adapter.inbound.http.dto.response.UserResponse;
import com.leviis.realworldexample.user.application.port.inbound.FollowUserUseCase;
import com.leviis.realworldexample.user.application.port.inbound.GetIsFollowingInformationUseCase;
import com.leviis.realworldexample.user.application.port.inbound.GetUserProfileUseCase;
import com.leviis.realworldexample.user.application.port.inbound.UnfollowUserUseCase;
import com.leviis.realworldexample.user.application.port.inbound.UpdateUserUseCase;
import com.leviis.realworldexample.user.application.query.GetIsFollowingInformationQuery;
import com.leviis.realworldexample.user.application.query.GetUserProfileQuery;
import com.leviis.realworldexample.utils.http.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public final class UserController {
    private final UpdateUserUseCase updateUserUseCase;
    private final GetUserProfileUseCase getUserProfileUseCase;
    private final GetIsFollowingInformationUseCase getIsFollowingInformationUseCase;
    private final FollowUserUseCase followUserUseCase;
    private final UnfollowUserUseCase unfollowUserUseCase;

    @GetMapping
    public ResponseEntity<ResponseWrapper<UserResponse>> getCurrentUser(
            @AuthenticationPrincipal final UserContext userContext) {
        var data = UserResponse.from(userContext);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>("Successfully retrieved current user", data));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<UserResponse>> updateUser(
            @AuthenticationPrincipal final UserContext userContext,
            @Valid @RequestBody final UpdateUserRequest request) {
        var data = updateUserUseCase.execute(request.intoCommand(userContext.getId()));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        "Successfully update user", UserResponse.from(data, userContext.getToken())));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseWrapper<GetUserProfileResponse>> getUserProfile(
            @PathVariable final String username, @AuthenticationPrincipal final UserContext userContext) {
        var foundUser = getUserProfileUseCase.execute(new GetUserProfileQuery(username));
        var isFollowing = getIsFollowingInformationUseCase.execute(
                new GetIsFollowingInformationQuery(userContext != null ? userContext.getId() : null, foundUser.id()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        "Successfully retrieved a user", GetUserProfileResponse.from(foundUser, isFollowing)));
    }

    @PostMapping("/{username}/follow")
    public ResponseEntity<ResponseWrapper<FollowUserResponse>> followUser(
            @AuthenticationPrincipal final UserContext userContext, @PathVariable final String username) {
        var foundFollowingUser = getUserProfileUseCase.execute(new GetUserProfileQuery(username));
        var isSuccessFollowUser = followUserUseCase.execute(userContext.intoUserDomain(), foundFollowingUser);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        "Successfully follow a user",
                        FollowUserResponse.from(foundFollowingUser, isSuccessFollowUser)));
    }

    @DeleteMapping("/{username}/follow")
    public ResponseEntity<ResponseWrapper<UnfollowResponse>> unfollowUser(
            @AuthenticationPrincipal final UserContext userContext, @PathVariable final String username) {
        var foundUnfollowingUser = getUserProfileUseCase.execute(new GetUserProfileQuery(username));
        var isSuccessUnfollow = unfollowUserUseCase.execute(userContext.getId(), foundUnfollowingUser.id());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        "Successfully unfollow a user",
                        UnfollowResponse.from(foundUnfollowingUser, isSuccessUnfollow)));
    }
}
