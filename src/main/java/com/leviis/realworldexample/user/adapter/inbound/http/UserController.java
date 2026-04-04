package com.leviis.realworldexample.user.adapter.inbound.http;

import com.leviis.realworldexample.user.adapter.inbound.http.dto.request.RegisterUserRequest;
import com.leviis.realworldexample.user.adapter.inbound.http.dto.response.RegisterUserResponse;
import com.leviis.realworldexample.user.application.port.inbound.RegisterUserUseCase;
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

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody final RegisterUserRequest request) {
        var data = registerUserUseCase.execute(request.intoRegisterUserCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(RegisterUserResponse.from(data));
    }
}
