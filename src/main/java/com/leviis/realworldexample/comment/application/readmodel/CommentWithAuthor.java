package com.leviis.realworldexample.comment.application.readmodel;

import com.leviis.realworldexample.comment.domain.Comment;
import com.leviis.realworldexample.user.domain.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Builder;
import org.jspecify.annotations.Nullable;

@Builder(setterPrefix = "set")
public record CommentWithAuthor(long id, LocalDateTime createdAt, LocalDateTime updatedAt, String body, Author author) {
    public static CommentWithAuthor from(final Comment comment, final User author, final boolean isFollowing) {
        return CommentWithAuthor.builder()
                .setId(comment.id())
                .setCreatedAt(comment.createdAt())
                .setUpdatedAt(comment.updatedAt())
                .setBody(comment.body())
                .setAuthor(Author.from(author, isFollowing))
                .build();
    }

    public static CommentWithAuthor from(final Comment comment, final User author) {
        return from(comment, author, false);
    }

    public static List<CommentWithAuthor> from(
            final List<Comment> foundComments, final List<User> authors, @Nullable final List<Long> followings) {
        final Map<Long, User> authorMap = authors.stream()
                .collect(Collectors.toMap(User::id, Function.identity(), (_, replacement) -> replacement));
        final Optional<Map<Long, Boolean>> followingMap = Optional.ofNullable(followings)
                .map(f -> f.stream()
                        .collect(Collectors.toMap(following -> following, _ -> true, (_, replacement) -> replacement)));

        final List<CommentWithAuthor> result = new ArrayList<>();
        for (final Comment comment : foundComments) {
            final boolean isFollowing =
                    followingMap.isPresent() && followingMap.get().getOrDefault(comment.authorId(), false);
            result.add(from(comment, authorMap.get(comment.authorId()), isFollowing));
        }
        return result;
    }

    @Builder(setterPrefix = "set")
    public record Author(String username, String bio, String image, boolean isFollowing) {
        public static Author from(final User author, final boolean isFollowing) {
            return Author.builder()
                    .setUsername(author.username())
                    .setBio(author.bio())
                    .setImage(author.image())
                    .setIsFollowing(isFollowing)
                    .build();
        }
    }
}
