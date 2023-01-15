package pl.kul.blog.infrastructure.repositories.comment;

import lombok.AllArgsConstructor;
import pl.kul.blog.domain.comment.Comment;
import pl.kul.blog.domain.comment.CommentRepository;

@AllArgsConstructor
public class DelegatingCommentRepository implements CommentRepository {
    private final JpaBasedCommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
}
