package com.leviis.realworldexample.user.application.port.outbound;

import com.leviis.realworldexample.user.domain.Password;

public interface PasswordService {
    String hashPassword(Password password);

    boolean compare(String rawPassword, String hashedPassword);
}
