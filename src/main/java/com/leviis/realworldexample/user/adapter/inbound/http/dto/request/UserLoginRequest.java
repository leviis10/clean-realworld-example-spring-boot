package com.leviis.realworldexample.user.adapter.inbound.http.dto.request;

import com.leviis.realworldexample.user.application.query.UserLoginQuery;
import jakarta.validation.constraints.Email;
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
    private static final int PASSWORD_MIN_LENGTH = 8;

    @Email(message = "Invalid email address")
    private String email;

    @Size(min = PASSWORD_MIN_LENGTH, message = "Password must be at least 8 characters long")
    private String password;

    public UserLoginQuery intoUserLoginQuery() {
        return new UserLoginQuery(this.email, this.password);
    }
}
