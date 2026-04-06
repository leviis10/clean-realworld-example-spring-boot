package com.leviis.realworldexample.user.adapter.inbound.http;

import com.leviis.realworldexample.user.adapter.inbound.http.dto.request.RegisterUserRequest;
import com.leviis.realworldexample.user.adapter.inbound.http.dto.request.UserLoginRequest;
import com.leviis.realworldexample.user.adapter.inbound.http.dto.response.UserResponse;
import com.leviis.realworldexample.user.application.port.inbound.RegisterUserUseCase;
import com.leviis.realworldexample.user.application.port.inbound.UserLoginUseCase;
import com.leviis.realworldexample.utils.http.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public final class UserController {
    private final RegisterUserUseCase registerUserUseCase;
    private final UserLoginUseCase userLoginUseCase;

    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper<UserResponse>> register(
            @Valid @RequestBody final RegisterUserRequest request) {
        var data = registerUserUseCase.execute(request.intoRegisterUserCommand());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>("Success registering new user", UserResponse.from(data)));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<UserResponse>> login(@Valid @RequestBody final UserLoginRequest request) {
        var user = userLoginUseCase.execute(request.intoUserLoginQuery());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>("Successfully logged in", UserResponse.from(user)));
    }
}
