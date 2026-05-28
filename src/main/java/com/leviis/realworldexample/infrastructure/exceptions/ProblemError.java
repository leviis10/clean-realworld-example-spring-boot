package com.leviis.realworldexample.infrastructure.exceptions;

import lombok.Builder;

@Builder(setterPrefix = "set")
public record ProblemError(String field, String code, String message, Object rejectedValue) {}
