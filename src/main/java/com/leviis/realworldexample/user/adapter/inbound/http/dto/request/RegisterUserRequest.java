package com.leviis.realworldexample.user.adapter.inbound.http.dto.request;

import com.leviis.realworldexample.user.adapter.inbound.http.dto.request.constants.ErrorMessages;
import com.leviis.realworldexample.user.application.command.RegisterUserCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least contains 1 lowercase character, 1 uppercase character, 1 digit, 1"
                    + " special characters, and at least have 8 characters long")
    @NotNull(message = ErrorMessages.NULL_PASSWORD_VALIDATION)
    private String password;

    @NotBlank(message = ErrorMessages.BLANK_USERNAME_VALIDATION)
    @NotNull(message = ErrorMessages.NULL_USERNAME_VALIDATION)
    private String username;

    public RegisterUserCommand intoRegisterUserCommand() {
        return new RegisterUserCommand(this.email, this.password, this.username);
    }
}
