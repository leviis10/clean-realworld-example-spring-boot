package com.leviis.realworldexample.user.adapter.outbound;

import com.leviis.realworldexample.user.application.port.outbound.TokenService;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class TokenServiceImpl implements TokenService {
    private final UserQueryRepository userQueryRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private String expiration;

    @Override
    public String generateToken(final User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public User getUserFrom(final String token) {
        var claims = getClaims(token);

        return userQueryRepository
                .findById(Long.valueOf(claims.getSubject()))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Claims getClaims(final String token) {
        var jwtParser = Jwts.parser().verifyWith((SecretKey) getSigningKey()).build();

        return jwtParser.parse(token).accept(Jws.CLAIMS).getPayload();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
