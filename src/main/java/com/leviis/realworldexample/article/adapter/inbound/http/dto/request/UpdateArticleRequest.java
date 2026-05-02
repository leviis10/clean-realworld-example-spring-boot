package com.leviis.realworldexample.article.adapter.inbound.http.dto.request;

import com.leviis.realworldexample.article.application.command.UpdateArticleCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public final class UpdateArticleRequest {
    @NotBlank(message = "title cannot be empty")
    private String title;

    @NotBlank(message = "description cannot be empty")
    private String description;

    @NotBlank(message = "body cannot be empty")
    private String body;

    @SuppressWarnings("unchecked")
    public <T> T into(final Class<T> target) {
        if (target == UpdateArticleCommand.UpdateDataDto.class) {
            return (T) UpdateArticleCommand.UpdateDataDto.builder()
                    .title(this.title)
                    .description(this.description)
                    .body(this.body)
                    .build();
        }

        throw new IllegalArgumentException("Cannot convert to " + target);
    }
}
