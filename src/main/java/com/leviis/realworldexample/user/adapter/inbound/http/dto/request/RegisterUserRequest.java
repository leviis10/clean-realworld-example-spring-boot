package com.leviis.realworldexample.user.adapter.inbound.http.dto.request;

import com.leviis.realworldexample.user.adapter.inbound.http.dto.request.constants.ErrorMessages;
import com.leviis.realworldexample.user.adapter.inbound.http.dto.request.constants.ValidationRules;
import com.leviis.realworldexample.user.application.command.RegisterUserCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Email(message = ErrorMessages.INVALID_EMAIL_VALIDATION)
    @NotNull(message = ErrorMessages.NULL_EMAIL_VALIDATION)
    private String email;

    @Size(min = ValidationRules.PASSWORD_MIN_LENGTH, message = ErrorMessages.MIN_PASSWORD_VALIDATION)
    @NotNull(message = ErrorMessages.NULL_PASSWORD_VALIDATION)
    private String password;

    @NotBlank(message = ErrorMessages.BLANK_USERNAME_VALIDATION)
    @NotNull(message = ErrorMessages.NULL_USERNAME_VALIDATION)
    private String username;

    public RegisterUserCommand intoRegisterUserCommand() {
        return new RegisterUserCommand(this.email, this.password, this.username);
    }
}
