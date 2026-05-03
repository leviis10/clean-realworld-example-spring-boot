package com.leviis.realworldexample.comment.adapter.inbound.http.dto.response;

import com.leviis.realworldexample.comment.application.readmodel.CommentWithAuthor;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateCommentResponse {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String body;
    private AuthorDto author;

    public static CreateCommentResponse from(final CommentWithAuthor comment) {
        return CreateCommentResponse.builder()
                .id(comment.id())
                .createdAt(comment.createdAt())
                .updatedAt(comment.updatedAt())
                .body(comment.body())
                .author(AuthorDto.from(comment.author()))
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
        private boolean isFollowing;

        public static AuthorDto from(final CommentWithAuthor.Author author) {
            return AuthorDto.builder()
                    .username(author.username())
                    .bio(author.bio())
                    .image(author.image())
                    .isFollowing(author.isFollowing())
                    .build();
        }
    }
}
