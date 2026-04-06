package com.leviis.realworldexample.user.adapter.outbound;

import com.leviis.realworldexample.user.application.port.outbound.PasswordService;
import com.leviis.realworldexample.user.domain.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class PasswordServiceImpl implements PasswordService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String hashPassword(final Password password) {
        return passwordEncoder.encode(password.value());
    }

    @Override
    public boolean compare(final String rawPassword, final String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
