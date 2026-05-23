package com.leviis.realworldexample.comment.adapter.inbound.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DeleteByIdAndArticleSlugRequest {
    @NotBlank(message = "`slug` request body cannot be blank")
    private String slug;
}
