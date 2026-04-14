package com.leviis.realworldexample.tag.adapter.inbound.http;

import com.leviis.realworldexample.tag.application.port.inbound.FindAllTagsUseCase;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.utils.http.ResponseWrapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public final class TagController {
    private final FindAllTagsUseCase findAllTagsUseCase;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<String>>> findAll() {
        var data = findAllTagsUseCase.execute();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        "Successfully retrieved all tags",
                        data.stream().map(Tag::name).toList()));
    }
}
