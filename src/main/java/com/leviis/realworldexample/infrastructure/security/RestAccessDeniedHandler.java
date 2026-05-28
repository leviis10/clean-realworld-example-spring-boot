package com.leviis.realworldexample.infrastructure.security;

import com.leviis.realworldexample.infrastructure.constants.HeaderConstants;
import com.leviis.realworldexample.utils.ProblemDetailUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Component
public final class RestAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @SuppressWarnings("PMD.LawOfDemeter")
    @Override
    public void handle(
            final HttpServletRequest request,
            final @NonNull HttpServletResponse response,
            final AccessDeniedException e)
            throws IOException {
        final HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        final ProblemDetail problemDetail = ProblemDetailUtils.constructProblemDetail(
                httpStatus, e.getMessage(), "about:blank", "Access Denied", request.getRequestURI());
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        response.addHeader(HeaderConstants.TRACE_ID_HEADER, MDC.get("traceId"));
        response.getOutputStream().write(objectMapper.writeValueAsBytes(problemDetail));
    }
}
