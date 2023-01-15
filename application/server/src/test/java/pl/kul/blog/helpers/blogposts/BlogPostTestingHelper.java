package pl.kul.blog.helpers.blogposts;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kul.blog.domain.comment.Comment;
import pl.kul.blog.domain.post.Post;
import pl.kul.blog.domain.post.PostRepository;
import pl.kul.blog.domain.user.account.UserAccount;
import pl.kul.blog.domain.user.account.UserAccountRepository;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class BlogPostTestingHelper {
    private final PostRepository postRepository;
    private final UserAccountRepository userAccountRepository;

    public Post exists(AlreadyExistingPost post) {
        UserAccount postedByUserAccount = userAccountRepository.findByUsername(post.postedBy.username)
            .get();

        Post newPost = Post.builder()
            .id(post.id)
            .subject(post.subject)
            .content(post.content)
            .postedBy(postedByUserAccount)
            .postedOn(Instant.now().minus(Duration.ofMinutes(2)))
            .build();

        return postRepository.save(newPost);
    }

    @Transactional
    public List<Comment> getCommentsOf(Post existingPost) {
        return postRepository.findById(existingPost.getId())
            .get()
            .getComments()
            .stream()
            .collect(Collectors.toList());
    }
}
