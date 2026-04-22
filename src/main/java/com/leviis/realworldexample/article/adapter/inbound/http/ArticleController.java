package com.leviis.realworldexample.article.adapter.inbound.http;

import com.leviis.realworldexample.article.adapter.inbound.http.dto.queryparameter.FindAllArticleQueryParameter;
import com.leviis.realworldexample.article.adapter.inbound.http.dto.response.FindAllArticleResponse;
import com.leviis.realworldexample.article.application.port.inbound.FindAllArticleUseCase;
import com.leviis.realworldexample.infrastructure.UserContext;
import com.leviis.realworldexample.utils.http.ResponseWrapper;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/articles")
public final class ArticleController {
    private final FindAllArticleUseCase findAllArticleUseCase;

    @GetMapping
    public ResponseEntity<ResponseWrapper<FindAllArticleResponse>> findAll(
            @AuthenticationPrincipal @Nullable final UserContext userContext,
            final FindAllArticleQueryParameter queryParameter) {
        var foundArticles = findAllArticleUseCase.execute(queryParameter.intoQuery(userContext));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        "Successfully retrieved article data", FindAllArticleResponse.from(foundArticles)));
    }
}
