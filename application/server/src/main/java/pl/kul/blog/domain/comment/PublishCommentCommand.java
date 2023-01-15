package pl.kul.blog.domain.comment;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class PublishCommentCommand {
    UUID commentId;
    UUID targetPostId;
    String content;
}
