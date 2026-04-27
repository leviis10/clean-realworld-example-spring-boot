package com.leviis.realworldexample.article.adapter.inbound.http.dto.response;

import com.leviis.realworldexample.article.application.query.ArticleWithBodyAndAuthor;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GetArticleResponse {
    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tags;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean isFavorite;
    private Long favoritesCount;
    private AuthorDto author;

    public static GetArticleResponse from(final ArticleWithBodyAndAuthor article) {
        return GetArticleResponse.builder()
                .slug(article.slug())
                .title(article.title())
                .description(article.description())
                .body(article.body())
                .tags(article.tags())
                .createdAt(article.createdAt())
                .updatedAt(article.updatedAt())
                .isFavorite(article.isFavorite())
                .favoritesCount(article.favoritesCount())
                .author(AuthorDto.from(article.author()))
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class AuthorDto {
        private String username;
        private String bio;
        private String image;
        private Boolean isFollowing;

        public static AuthorDto from(final ArticleWithBodyAndAuthor.Author author) {
            return AuthorDto.builder()
                    .username(author.username())
                    .bio(author.bio())
                    .image(author.image())
                    .isFollowing(author.isFollowing())
                    .build();
        }
    }
}
