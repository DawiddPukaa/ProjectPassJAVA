package pl.kul.blog.domain.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kul.blog.domain.post.Post;
import pl.kul.blog.domain.post.PostRepository;
import pl.kul.blog.domain.user.CurrentUserProvider;
import pl.kul.blog.infrastructure.clock.ClockProvider;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CommentPublisher {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CurrentUserProvider currentUserProvider;
    private final ClockProvider clockProvider;

    @Transactional
    public Comment publishComment(PublishCommentCommand command) {
        Post post = postRepository.findById(command.getTargetPostId())
            .orElseThrow(() -> new IllegalStateException("Post with such id does not exist"));

        Comment publishedComment = commentRepository.save(Comment.builder()
            .id(command.getCommentId())
            .postedWithin(post)
            .postedBy(currentUserProvider.getCurrentUser())
            .postedOn(clockProvider.instant())
            .content(command.getContent())
            .build());

        return publishedComment;
    }
}
