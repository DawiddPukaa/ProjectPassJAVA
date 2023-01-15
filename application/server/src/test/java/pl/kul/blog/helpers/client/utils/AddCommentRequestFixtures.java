package pl.kul.blog.helpers.client.utils;

import java.util.UUID;

public class AddCommentRequestFixtures {
    public static AddCommentRequest validRequest() {
        return new AddCommentRequest()
            .withCommentId(UUID.randomUUID())
            .withBody(new AddCommentRequest.RequestBody()
                .withAddToPostId(UUID.randomUUID())
                .withContent("Comment content")
            );
    }
}
