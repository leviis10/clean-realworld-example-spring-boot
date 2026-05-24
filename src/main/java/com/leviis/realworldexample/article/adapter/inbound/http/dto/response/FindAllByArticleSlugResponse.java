package com.leviis.realworldexample.article.adapter.inbound.http.dto.response;

import com.leviis.realworldexample.article.application.readmodel.CommentWithAuthor;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(setterPrefix = "set")
public class FindAllByArticleSlugResponse {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String body;
    private AuthorDto author;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder(setterPrefix = "set")
    public static class AuthorDto {
        private String username;
        private String bio;
        private String image;
        private Boolean isFollowing;

        public static AuthorDto from(final CommentWithAuthor.Author author) {
            return AuthorDto.builder()
                    .setUsername(author.username())
                    .setBio(author.bio())
                    .setImage(author.image())
                    .setIsFollowing(author.isFollowing())
                    .build();
        }
    }

    public static List<FindAllByArticleSlugResponse> from(final List<CommentWithAuthor> comments) {
        return comments.stream().map(FindAllByArticleSlugResponse::from).toList();
    }

    private static FindAllByArticleSlugResponse from(final CommentWithAuthor comment) {
        return FindAllByArticleSlugResponse.builder()
                .setId(comment.id())
                .setCreatedAt(comment.createdAt())
                .setUpdatedAt(comment.updatedAt())
                .setBody(comment.body())
                .setAuthor(AuthorDto.from(comment.author()))
                .build();
    }
}
