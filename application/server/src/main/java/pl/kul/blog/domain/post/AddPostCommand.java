package pl.kul.blog.domain.post;

import lombok.Value;

import java.util.UUID;

@Value
public class AddPostCommand {
    UUID postId;
    String subject;
    String content;
}
