package com.leviis.realworldexample.tag.domain;

import lombok.Builder;

@Builder(setterPrefix = "set")
public record Tag(Long id, String name) {}
