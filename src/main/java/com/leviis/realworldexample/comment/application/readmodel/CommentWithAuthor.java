package com.leviis.realworldexample.comment.application.readmodel;

import com.leviis.realworldexample.comment.domain.Comment;
import com.leviis.realworldexample.user.domain.User;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder(setterPrefix = "set")
public record CommentWithAuthor(long id, LocalDateTime createdAt, LocalDateTime updatedAt, String body, Author author) {
    @Builder(setterPrefix = "set")
    public record Author(String username, String bio, String image, boolean isFollowing) {
        public static Author from(final User author) {
            return Author.builder()
                    .setUsername(author.username())
                    .setBio(author.bio())
                    .setImage(author.image())
                    .setIsFollowing(false)
                    .build();
        }
    }

    public static CommentWithAuthor from(final Comment newComment, final User author) {
        return CommentWithAuthor.builder()
                .setId(newComment.id())
                .setCreatedAt(newComment.createdAt())
                .setUpdatedAt(newComment.updatedAt())
                .setBody(newComment.body())
                .setAuthor(Author.from(author))
                .build();
    }
}
