package com.leviis.realworldexample.user.adapter.outbound;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.leviis.realworldexample.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {
    @InjectMocks
    private TokenServiceImpl tokenService;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(
                tokenService,
                "secretKey",
                "2dd26c9c57b3a31cc5afea02308cb4c9e346d11c03b637ed265d71b151182f2ede412bae547439ecc822a316395f7777695e2fb52bb0a152674928add20c0505d108e675e63000b87d19e0047808da592979f55f0cc85826f9564486dd2667e154e8991efe688179e7438d300843d5bcf6d41117cf9b75b267b1d4fdf56818a85e4df72611e5ed62b82a8cde8e4a0bd5a125dcf547645d22afdc0556ae51e7b913989c1e57a425840f7bfdf26dd8daf4f0e6567a6e9de3b18266f57e21b9d2a7f9ee47c18909bbed2f71e328a2bccea0fa857550ddee3ede9894f78d0d5edf33cc09a49cdff096062d51886483300502c002a45fa462dbaf0607eb860ca4660d\n");
        ReflectionTestUtils.setField(tokenService, "expiration", "99");
    }

    @Nested
    class GenerateToken {
        @Test
        public void generateToken_positiveCase_returnTokenWithSameSubjectAsUserId() {
            User user = User.builder().setId(1L).build();

            String response = tokenService.generateToken(user);

            Claims claims = getClaims(response);
            assertEquals(user.id(), Long.valueOf(claims.getSubject()));
        }
    }

    private Claims getClaims(final String token) {
        final JwtParser jwtParser =
                Jwts.parser().verifyWith((SecretKey) getSigningKey()).build();

        return jwtParser.parse(token).accept(Jws.CLAIMS).getPayload();
    }

    private Key getSigningKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(
                "2dd26c9c57b3a31cc5afea02308cb4c9e346d11c03b637ed265d71b151182f2ede412bae547439ecc822a316395f7777695e2fb52bb0a152674928add20c0505d108e675e63000b87d19e0047808da592979f55f0cc85826f9564486dd2667e154e8991efe688179e7438d300843d5bcf6d41117cf9b75b267b1d4fdf56818a85e4df72611e5ed62b82a8cde8e4a0bd5a125dcf547645d22afdc0556ae51e7b913989c1e57a425840f7bfdf26dd8daf4f0e6567a6e9de3b18266f57e21b9d2a7f9ee47c18909bbed2f71e328a2bccea0fa857550ddee3ede9894f78d0d5edf33cc09a49cdff096062d51886483300502c002a45fa462dbaf0607eb860ca4660d\n");

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
