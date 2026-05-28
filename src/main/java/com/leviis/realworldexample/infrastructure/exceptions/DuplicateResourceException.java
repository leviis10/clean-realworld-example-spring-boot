package com.leviis.realworldexample.infrastructure.exceptions;

import java.io.Serial;
import java.util.List;
import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -3762459250517073551L;

    private final List<ProblemError> errors;

    public DuplicateResourceException(final String message, final List<ProblemError> errors) {
        super(message);
        this.errors = List.copyOf(errors);
    }
}
