package pl.kul.blog.infrastructure.api.posts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import pl.kul.blog.domain.post.AddPostCommand;

import java.util.UUID;

@Value
public class NewPostRequest {
    String subject;
    String content;

    @JsonCreator
    public NewPostRequest(
        @JsonProperty("subject") String subject,
        @JsonProperty("content") String content
    ) {
        this.subject = subject;
        this.content = content;
    }

    public AddPostCommand toDomain(UUID postId) {
        return new AddPostCommand(postId, subject, content);
    }
}
