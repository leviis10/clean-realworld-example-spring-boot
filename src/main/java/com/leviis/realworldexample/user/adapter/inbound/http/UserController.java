package com.leviis.realworldexample.user.adapter.inbound.http;

import com.leviis.realworldexample.infrastructure.UserContext;
import com.leviis.realworldexample.user.adapter.inbound.http.dto.response.UserResponse;
import com.leviis.realworldexample.utils.http.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public final class UserController {
    @GetMapping
    public ResponseEntity<ResponseWrapper<UserResponse>> getCurrentUser(
            @AuthenticationPrincipal final UserContext userContext) {
        var data = UserResponse.from(userContext);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>("Successfully retrieved current user", data));
    }
}
