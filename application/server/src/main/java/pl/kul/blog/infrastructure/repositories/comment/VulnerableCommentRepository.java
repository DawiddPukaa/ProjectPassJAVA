package pl.kul.blog.infrastructure.repositories.comment;

import lombok.AllArgsConstructor;
import pl.kul.blog.domain.comment.Comment;
import pl.kul.blog.domain.comment.CommentRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@AllArgsConstructor
public class VulnerableCommentRepository implements CommentRepository {
    private final DataSource dataSource;

    @Override
    public Comment save(Comment comment) {
        try (
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
        ) {
            statement.execute(String.format(
                """
                    insert into comment (content, posted_by_id, posted_on, posted_within_id, version, id) 
                    values ('%s', X'%s', '%s', X'%s', %s, X'%s')
                    """,
                comment.getContent(),
                comment.getPostedBy().getId().toString().replaceAll("-", ""),
                comment.getPostedOn().toString(),
                comment.getPostedWithin().getId().toString().replaceAll("-", ""),
                Optional.ofNullable(comment.getVersion()).orElse(0L),
                comment.getId().toString().replaceAll("-", "")
                )
            );

            return comment;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
