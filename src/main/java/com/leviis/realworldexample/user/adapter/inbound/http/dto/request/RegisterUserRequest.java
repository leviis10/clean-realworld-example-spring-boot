package com.leviis.realworldexample.user.adapter.inbound.http.dto.request;

import com.leviis.realworldexample.user.application.command.RegisterUserCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public final class RegisterUserRequest {
    private static final int PASSWORD_MIN_LENGTH = 8;

    @Email(message = "Invalid email")
    private String email;

    @Size(min = PASSWORD_MIN_LENGTH)
    private String password;

    @NotBlank(message = "Username can't be blank!")
    private String username;

    public RegisterUserCommand intoRegisterUserCommand() {
        return new RegisterUserCommand(this.email, this.password, this.username);
    }
}
