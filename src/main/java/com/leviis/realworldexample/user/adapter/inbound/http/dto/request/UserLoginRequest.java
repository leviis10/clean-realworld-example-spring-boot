package com.leviis.realworldexample.user.adapter.inbound.http.dto.request;

import com.leviis.realworldexample.user.adapter.inbound.http.dto.request.constants.ErrorMessages;
import com.leviis.realworldexample.user.adapter.inbound.http.dto.request.constants.ValidationRules;
import com.leviis.realworldexample.user.application.query.UserLoginQuery;
import com.leviis.realworldexample.user.domain.Email;
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
public final class UserLoginRequest {
    @jakarta.validation.constraints.Email(message = ErrorMessages.INVALID_EMAIL_VALIDATION)
    @NotNull(message = "Email cannot be null")
    private String email;

    @Size(min = ValidationRules.PASSWORD_MIN_LENGTH, message = ErrorMessages.MIN_PASSWORD_VALIDATION)
    @NotNull(message = ErrorMessages.NULL_PASSWORD_VALIDATION)
    private String password;

    public UserLoginQuery intoUserLoginQuery() {
        return new UserLoginQuery(new Email(this.email), this.password);
    }
}
