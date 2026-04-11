package com.leviis.realworldexample.user.adapter.inbound.http.dto.request;

import com.leviis.realworldexample.user.adapter.inbound.http.dto.request.constants.ErrorMessages;
import com.leviis.realworldexample.user.adapter.inbound.http.dto.request.constants.ValidationRules;
import com.leviis.realworldexample.user.application.command.UpdateUserCommand;
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
public final class UpdateUserRequest {
    @Email(message = ErrorMessages.INVALID_EMAIL_VALIDATION)
    private String email;

    @NotBlank(message = ErrorMessages.BLANK_USERNAME_VALIDATION)
    private String username;

    @Size(min = ValidationRules.PASSWORD_MIN_LENGTH, message = ErrorMessages.MIN_PASSWORD_VALIDATION)
    private String password;

    private String image;

    private String bio;

    public UpdateUserCommand intoCommand(final Long id) {
        return UpdateUserCommand.builder()
                .setId(id)
                .setEmail(this.email)
                .setUsername(this.username)
                .setPassword(this.password)
                .setImage(this.image)
                .setBio(this.bio)
                .build();
    }
}
