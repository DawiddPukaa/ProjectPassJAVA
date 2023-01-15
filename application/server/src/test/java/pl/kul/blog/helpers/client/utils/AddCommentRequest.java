package pl.kul.blog.helpers.client.utils;

import pl.kul.blog.domain.post.Post;

import java.util.UUID;
import java.util.function.Function;

public class AddCommentRequest {
    public UUID commentId;
    public RequestBody body;

    public AddCommentRequest withCommentId(UUID commentId) {
        this.commentId = commentId;
        return this;
    }

    public AddCommentRequest withBody(RequestBody body) {
        this.body = body;
        return this;
    }

    public AddCommentRequest withBodyBut(Function<RequestBody, RequestBody> function) {
        body = function.apply(body);
        return this;
    }

    public static class RequestBody {
        public UUID addToPostId;
        public String content;


        public RequestBody withAddToPostId(UUID addToPostId) {
            this.addToPostId = addToPostId;
            return this;
        }

        public RequestBody withAddToPostId(Post post) {
            return withAddToPostId(post.getId());
        }

        public RequestBody withContent(String content) {
            this.content = content;
            return this;
        }
    }
}
