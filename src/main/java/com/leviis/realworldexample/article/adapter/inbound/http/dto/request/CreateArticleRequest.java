package com.leviis.realworldexample.article.adapter.inbound.http.dto.request;

import com.leviis.realworldexample.article.application.command.CreateArticleCommand;
import com.leviis.realworldexample.user.domain.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public final class CreateArticleRequest {
    @NotNull(message = "Title cannot be null")
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Description cannot be null")
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Body cannot be null")
    @NotEmpty(message = "Body cannot be empty")
    private String body;

    private List<String> tags;

    public CreateArticleCommand intoCreateArticleCommand(final User author) {
        return CreateArticleCommand.builder()
                .setTitle(this.title)
                .setDescription(this.description)
                .setBody(this.body)
                .setTags(this.tags)
                .setAuthor(author)
                .build();
    }
}
