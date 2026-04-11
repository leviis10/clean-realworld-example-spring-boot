package com.leviis.realworldexample.user.adapter.inbound.http;

import com.leviis.realworldexample.infrastructure.UserContext;
import com.leviis.realworldexample.user.adapter.inbound.http.dto.request.UpdateUserRequest;
import com.leviis.realworldexample.user.adapter.inbound.http.dto.response.UserResponse;
import com.leviis.realworldexample.user.application.port.inbound.UpdateUserUseCase;
import com.leviis.realworldexample.utils.http.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public final class UserController {
    private final UpdateUserUseCase updateUserUseCase;

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
}
