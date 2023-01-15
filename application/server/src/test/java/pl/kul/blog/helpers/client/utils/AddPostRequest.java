package pl.kul.blog.helpers.client.utils;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class AddPostRequest {
    UUID postId;
    Body body;

    @Value
    @Builder
    static class Body {
        String subject;
        String content;
    }
}
