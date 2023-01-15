package pl.kul.blog.domain.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
public class PostFinder {
    private final PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional
    public PostWithComments findById(UUID postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalStateException("There is no post with such id"));
        return new PostWithComments(post, post.getComments());
    }
}
