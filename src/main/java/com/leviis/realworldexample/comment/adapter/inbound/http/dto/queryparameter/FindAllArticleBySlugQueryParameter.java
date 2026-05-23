package com.leviis.realworldexample.comment.adapter.inbound.http.dto.queryparameter;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.BindParam;

public record FindAllArticleBySlugQueryParameter(
        @BindParam("article_slug") @NotBlank(message = "article_slug query parameter cannot be blank.")
        String articleSlug) {}
