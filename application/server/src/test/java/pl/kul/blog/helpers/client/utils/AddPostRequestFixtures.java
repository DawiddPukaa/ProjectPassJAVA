package pl.kul.blog.helpers.client.utils;

import java.util.UUID;

public class AddPostRequestFixtures {
    public static AddPostRequest.AddPostRequestBuilder validRequest() {
        return AddPostRequest.builder()
            .postId(UUID.randomUUID())
            .body(AddPostRequest.Body.builder()
                .subject("Some subject")
                .content("Some content")
                .build()
            );
    }
}
