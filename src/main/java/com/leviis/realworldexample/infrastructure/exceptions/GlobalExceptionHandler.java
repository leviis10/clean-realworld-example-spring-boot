package com.leviis.realworldexample.infrastructure.exceptions;

import com.leviis.realworldexample.article.application.exceptions.ArticleNotFoundException;
import com.leviis.realworldexample.infrastructure.constants.ErrorTypeConstants;
import com.leviis.realworldexample.user.application.exceptions.IncorrectCredentialsException;
import com.leviis.realworldexample.utils.ProblemDetailUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@NoArgsConstructor
@Slf4j
@RestControllerAdvice
public final class GlobalExceptionHandler {
    private static final List<String> SENSITIVE_FIELD = List.of("password");

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e, final HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_CONTENT;
        final List<ProblemError> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> ProblemError.builder()
                        .setField(fieldError.getField())
                        .setCode("INVALID_FORMAT")
                        .setMessage(fieldError.getDefaultMessage())
                        .setRejectedValue(maskSensitiveField(fieldError.getField(), fieldError.getRejectedValue()))
                        .build())
                .toList();

        final ProblemDetail response = ProblemDetailUtils.constructProblemDetail(
                httpStatus,
                "One or more validation errors",
                ErrorTypeConstants.ABOUT_BLANK,
                "request body validation error",
                request.getRequestURI(),
                errors);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ProblemDetail> handleHttpMediaTypeNotSupportedException(
            final HttpMediaTypeNotSupportedException e, final HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        final ProblemDetail response = ProblemDetailUtils.constructProblemDetail(
                httpStatus,
                e.getMessage(),
                ErrorTypeConstants.ABOUT_BLANK,
                "Unsupported request body type",
                request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMediaTypeNotAcceptableException(
            final HttpMediaTypeNotAcceptableException e, final HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
        final ProblemDetail response = ProblemDetailUtils.constructProblemDetail(
                httpStatus,
                e.getMessage(),
                ErrorTypeConstants.ABOUT_BLANK,
                "Unsupported response expected response type",
                request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ProblemDetail> handleHttpRequestMethodNotSupportedException(
            final HttpRequestMethodNotSupportedException e, final HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        final ProblemDetail response = ProblemDetailUtils.constructProblemDetail(
                httpStatus,
                e.getMessage(),
                ErrorTypeConstants.ABOUT_BLANK,
                "Unsupported http method",
                request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException e, final HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final ProblemDetail response = ProblemDetailUtils.constructProblemDetail(
                httpStatus,
                e.getMessage(),
                ErrorTypeConstants.ABOUT_BLANK,
                "Malformed request",
                request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ProblemDetail> handleDuplicateResourceException(
            final DuplicateResourceException e, final HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.CONFLICT;
        final ProblemDetail response = ProblemDetailUtils.constructProblemDetail(
                httpStatus,
                "One or more data is already exists",
                ErrorTypeConstants.ABOUT_BLANK,
                e.getMessage(),
                request.getRequestURI(),
                e.getErrors());

        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoResourceFoundException(
            final NoResourceFoundException e, final HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        final ProblemDetail response = ProblemDetailUtils.constructProblemDetail(
                httpStatus, e.getMessage(), ErrorTypeConstants.ABOUT_BLANK, "URI Not Found", request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleArticleNotFoundException(
            final ArticleNotFoundException e, final HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        final ProblemDetail response = ProblemDetailUtils.constructProblemDetail(
                httpStatus,
                e.getMessage(),
                ErrorTypeConstants.ABOUT_BLANK,
                "Article Not Found",
                request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<ProblemDetail> handleIncorrectCredentialsException(
            final IncorrectCredentialsException e, final HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        final ProblemDetail response = ProblemDetailUtils.constructProblemDetail(
                httpStatus,
                e.getMessage(),
                ErrorTypeConstants.ABOUT_BLANK,
                "Failed to authenticate",
                request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(final Exception e, final HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final ProblemDetail response = ProblemDetailUtils.constructProblemDetail(
                httpStatus,
                e.getMessage(),
                ErrorTypeConstants.ABOUT_BLANK,
                "Something went wrong",
                request.getRequestURI());

        if (log.isErrorEnabled()) {
            log.error(e.getMessage(), e);
        }
        return ResponseEntity.status(httpStatus).body(response);
    }

    private Object maskSensitiveField(final String field, final Object value) {
        if (SENSITIVE_FIELD.contains(field)) {
            return "<masked>";
        }

        return value;
    }
}
