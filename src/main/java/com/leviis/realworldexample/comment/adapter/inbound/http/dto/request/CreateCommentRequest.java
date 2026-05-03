package com.leviis.realworldexample.comment.adapter.inbound.http.dto.request;

import com.leviis.realworldexample.comment.domain.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public final class CreateCommentRequest {
    @NotBlank(message = "Body cannot be blank.")
    private String body;

    @NotBlank(message = "Slug cannot be empty")
    private String slug;

    @SuppressWarnings("unchecked")
    public <T> T into(final Class<T> target) {
        if (target == Comment.class) {
            return (T) intoCommentDomain();
        }

        throw new IllegalArgumentException("Cast to " + target + " is not supported");
    }

    private Comment intoCommentDomain() {
        return Comment.builder().setBody(body).build();
    }
}
