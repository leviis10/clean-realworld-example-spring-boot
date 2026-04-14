package com.leviis.realworldexample.infrastructure;

import com.leviis.realworldexample.user.application.port.outbound.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public final class JwtFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain)
            throws ServletException, IOException {
        var token = getTokenFrom(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        var foundUser = tokenService.getUserFrom(token);

        var userContext = UserContext.from(foundUser, token);
        var authToken = new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private String getTokenFrom(final HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring("Bearer ".length());
    }
}
