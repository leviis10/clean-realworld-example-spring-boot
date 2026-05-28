package com.leviis.realworldexample.infrastructure.filter;

import com.leviis.realworldexample.infrastructure.UserContext;
import com.leviis.realworldexample.infrastructure.security.RestAuthenticationEntryPoint;
import com.leviis.realworldexample.user.application.port.outbound.TokenService;
import com.leviis.realworldexample.user.domain.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public final class JwtFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final String token = getTokenFrom(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }
            final User foundUser = tokenService.getUserFrom(token);

            final UserContext userContext = UserContext.from(foundUser, token);
            final UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            restAuthenticationEntryPoint.commence(
                    request, response, new CredentialsExpiredException(e.getMessage(), e));
        } catch (SignatureException e) {
            restAuthenticationEntryPoint.commence(request, response, new BadCredentialsException(e.getMessage(), e));
        }
    }

    private @Nullable String getTokenFrom(final HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }

        return authorizationHeader.substring("Bearer ".length());
    }
}
