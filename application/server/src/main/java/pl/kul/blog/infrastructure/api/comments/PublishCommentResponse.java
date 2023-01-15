package pl.kul.blog.infrastructure.api.comments;

import lombok.Builder;
import lombok.Value;
import pl.kul.blog.domain.comment.Comment;

import java.util.UUID;

@Value
@Builder
public class PublishCommentResponse {
    UUID commentId;

    public static PublishCommentResponse fromDomain(Comment comment) {
        return PublishCommentResponse.builder()
            .commentId(comment.getId())
            .build();
    }
}
