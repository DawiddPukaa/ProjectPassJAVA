package pl.kul.blog.infrastructure.api.comments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import pl.kul.blog.domain.comment.PublishCommentCommand;

import java.util.UUID;

@Value
public class PublishCommentRequest {
    UUID addToPostId;
    String content;

    @JsonCreator
    public PublishCommentRequest(
        @JsonProperty("addToPostId") UUID addToPostId,
        @JsonProperty("content") String content
    ) {
        this.addToPostId = addToPostId;
        this.content = content;
    }

    public PublishCommentCommand toDomain(UUID commentId) {
        return PublishCommentCommand.builder()
            .commentId(commentId)
            .targetPostId(addToPostId)
            .content(content)
            .build();
    }
}
