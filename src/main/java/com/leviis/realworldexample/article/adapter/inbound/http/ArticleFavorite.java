package com.leviis.realworldexample.article.adapter.inbound.http;

import com.leviis.realworldexample.article.adapter.inbound.http.dto.response.ArticleResponse;
import com.leviis.realworldexample.article.application.command.AddArticleToFavoriteCommand;
import com.leviis.realworldexample.article.application.port.inbound.AddArticleToFavoriteUseCase;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithBodyAndAuthor;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.infrastructure.UserContext;
import com.leviis.realworldexample.utils.SlugUtils;
import com.leviis.realworldexample.utils.http.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/articles/{slug}/favorite")
public final class ArticleFavorite {
    private final AddArticleToFavoriteUseCase addArticleToFavoriteUseCase;

    @PostMapping
    public ResponseEntity<ResponseWrapper<ArticleResponse>> add(
            @AuthenticationPrincipal final UserContext userContext, @PathVariable final String slug) {
        final ArticleWithBodyAndAuthor favoriteArticle =
                addArticleToFavoriteUseCase.execute(AddArticleToFavoriteCommand.builder()
                        .setAuthenticatedUser(userContext.intoUserDomain())
                        .setArticleSlug(new Slug(SlugUtils.getTitleFrom(slug), SlugUtils.getIdFrom(slug)))
                        .build());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(
                        "Successfully add an article into favorite", ArticleResponse.from(favoriteArticle)));
    }
}
