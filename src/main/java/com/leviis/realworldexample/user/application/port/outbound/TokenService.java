package com.leviis.realworldexample.user.application.port.outbound;

import com.leviis.realworldexample.user.domain.User;

public interface TokenService {
    String generateToken(User user);
}
