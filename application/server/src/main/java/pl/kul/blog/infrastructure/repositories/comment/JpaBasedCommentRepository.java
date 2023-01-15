package pl.kul.blog.infrastructure.repositories.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kul.blog.domain.comment.Comment;

import java.util.UUID;

public interface JpaBasedCommentRepository extends JpaRepository<Comment, UUID> {
}
