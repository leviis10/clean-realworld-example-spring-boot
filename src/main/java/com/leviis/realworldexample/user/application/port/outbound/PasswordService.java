package com.leviis.realworldexample.user.application.port.outbound;

import com.leviis.realworldexample.user.domain.RawPassword;

public interface PasswordService {
    String hashPassword(RawPassword password);

    boolean compare(String rawPassword, String hashedPassword);
}
