package pl.kul.blog.domain.post;

import lombok.Value;
import pl.kul.blog.domain.comment.Comment;

import java.util.Set;

@Value
public class PostWithComments {
    Post post;
    Set<Comment> comments;
}
