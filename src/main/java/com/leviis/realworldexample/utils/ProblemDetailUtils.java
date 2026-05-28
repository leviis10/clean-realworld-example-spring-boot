package com.leviis.realworldexample.utils;

import java.net.URI;
import org.jspecify.annotations.Nullable;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public final class ProblemDetailUtils {
    private ProblemDetailUtils() {}

    public static ProblemDetail constructProblemDetail(
            final HttpStatus httpStatus,
            final String detail,
            final String type,
            final String title,
            final String instance,
            @Nullable final Object errors,
            final String traceId) {
        final ProblemDetail response = ProblemDetail.forStatusAndDetail(httpStatus, detail);
        response.setType(URI.create(type));
        response.setTitle(title);
        response.setInstance(URI.create(instance));
        response.setProperty("traceId", traceId);
        response.setProperty("errors", errors);

        return response;
    }

    public static ProblemDetail constructProblemDetail(
            final HttpStatus httpStatus,
            final String detail,
            final String type,
            final String title,
            final String instance,
            @Nullable final Object errors) {
        return constructProblemDetail(httpStatus, detail, type, title, instance, errors, MDC.get("traceId"));
    }

    public static ProblemDetail constructProblemDetail(
            final HttpStatus httpStatus,
            final String detail,
            final String type,
            final String title,
            final String instance) {
        return constructProblemDetail(httpStatus, detail, type, title, instance, null);
    }
}
