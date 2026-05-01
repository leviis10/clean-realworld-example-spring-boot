package com.leviis.realworldexample.article.adapter.inbound.http;

import com.leviis.realworldexample.article.adapter.inbound.http.dto.queryparameter.FeedArticleQueryParameter;
import com.leviis.realworldexample.article.adapter.inbound.http.dto.queryparameter.FindAllArticleQueryParameter;
import com.leviis.realworldexample.article.adapter.inbound.http.dto.request.CreateArticleRequest;
import com.leviis.realworldexample.article.adapter.inbound.http.dto.response.CreateArticleResponse;
import com.leviis.realworldexample.article.adapter.inbound.http.dto.response.FindAllArticleResponse;
import com.leviis.realworldexample.article.adapter.inbound.http.dto.response.FindAllFeedArticleResponse;
import com.leviis.realworldexample.article.adapter.inbound.http.dto.response.GetArticleResponse;
import com.leviis.realworldexample.article.application.port.inbound.CreateArticleUseCase;
import com.leviis.realworldexample.article.application.port.inbound.FindAllArticleUseCase;
import com.leviis.realworldexample.article.application.port.inbound.FindAllFeedArticleUseCase;
import com.leviis.realworldexample.article.application.port.inbound.GetArticleUseCase;
import com.leviis.realworldexample.article.application.query.GetArticleQuery;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithAuthor;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithBodyAndAuthor;
import com.leviis.realworldexample.infrastructure.UserContext;
import com.leviis.realworldexample.user.domain.User;
import com.leviis.realworldexample.utils.http.ResponseWrapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/articles")
public final class ArticleController {
    private final FindAllArticleUseCase findAllArticleUseCase;
    private final FindAllFeedArticleUseCase findAllFeedArticleUseCase;
    private final GetArticleUseCase getArticleUseCase;
    private final CreateArticleUseCase createArticleUseCase;

    @GetMapping
    public ResponseEntity<ResponseWrapper<FindAllArticleResponse>> findAll(
            @AuthenticationPrincipal @Nullable final UserContext userContext,
            final FindAllArticleQueryParameter queryParameter) {
        final List<ArticleWithAuthor> foundArticles =
                findAllArticleUseCase.execute(queryParameter.intoQuery(userContext));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        "Successfully retrieved article data", FindAllArticleResponse.from(foundArticles)));
    }

    @GetMapping("/feed")
    public ResponseEntity<ResponseWrapper<FindAllFeedArticleResponse>> findAllFeed(
            @AuthenticationPrincipal final UserContext userContext, final FeedArticleQueryParameter queryParameter) {
        final List<ArticleWithAuthor> foundFeedArticle =
                findAllFeedArticleUseCase.execute(queryParameter.intoQuery(userContext));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        "Successfully retrieved feeds", FindAllFeedArticleResponse.from(foundFeedArticle)));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ResponseWrapper<GetArticleResponse>> getArticle(
            @Nullable @AuthenticationPrincipal final UserContext userContext, @PathVariable final String slug) {
        final User authenticatedUser = userContext == null ? null : userContext.intoUserDomain();
        final ArticleWithBodyAndAuthor foundArticle =
                getArticleUseCase.execute(GetArticleQuery.from(authenticatedUser, slug));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        "Successfully retrieved an article", GetArticleResponse.from(foundArticle)));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<CreateArticleResponse>> create(
            @AuthenticationPrincipal final UserContext userContext,
            @Valid @RequestBody final CreateArticleRequest request) {
        final ArticleWithBodyAndAuthor newArticle =
                createArticleUseCase.execute(request.intoCreateArticleCommand(userContext.intoUserDomain()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(
                        "Successfully created new article", CreateArticleResponse.from(newArticle)));
    }
}
