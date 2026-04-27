package com.leviis.realworldexample.article.adapter.inbound.http.dto.response;

import com.leviis.realworldexample.article.application.query.ArticleWithAuthor;
import com.leviis.realworldexample.article.application.query.ArticleWithAuthor.Author;
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
public class FindAllArticleResponse {
    private List<ArticleDto> articles;
    private Integer articleCount;

    public static FindAllArticleResponse from(final List<ArticleWithAuthor> articles) {
        var articlesData = articles.stream().map(ArticleDto::from).toList();

        return FindAllArticleResponse.builder()
                .articles(articlesData)
                .articleCount(articles.size())
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    static class ArticleDto {
        private String slug;
        private String title;
        private String description;
        private List<String> tags;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;
        private Boolean isFavorite;
        private Long favoriteCount;
        private AuthorDto author;

        public static ArticleDto from(final ArticleWithAuthor article) {
            return ArticleDto.builder()
                    .slug(article.slug())
                    .title(article.title())
                    .description(article.description())
                    .tags(article.tags())
                    .createdAt(article.createdAt())
                    .updatedAt(article.updatedAt())
                    .isFavorite(article.isFavorite())
                    .favoriteCount(article.favoriteCount())
                    .author(AuthorDto.from(article.author()))
                    .build();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    static class AuthorDto {
        private String username;
        private String bio;
        private String image;
        private Boolean isFollowing;

        public static AuthorDto from(final Author author) {
            return AuthorDto.builder()
                    .username(author.username())
                    .bio(author.bio())
                    .image(author.image())
                    .isFollowing(author.isFollowing())
                    .build();
        }
    }
}
