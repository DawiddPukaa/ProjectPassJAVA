package pl.kul.blog.domain.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kul.blog.domain.user.CurrentUserProvider;
import pl.kul.blog.domain.user.account.UserAccount;
import pl.kul.blog.infrastructure.clock.ClockProvider;

import javax.transaction.Transactional;

@Component
@AllArgsConstructor
public class PostCreator {
    private final PostRepository postRepository;
    private final ClockProvider clockProvider;
    private final CurrentUserProvider currentUserProvider;

    @Transactional
    public AddedPost newPost(AddPostCommand command) {
        UserAccount currentUser = currentUserProvider.getCurrentUser();

        Post newPost = Post.builder()
            .id(command.getPostId())
            .subject(command.getSubject())
            .content(command.getContent())
            .postedBy(currentUser)
            .postedOn(clockProvider.instant())
            .build();

        Post addedPost = postRepository.save(newPost);

        return new AddedPost(addedPost);
    }
}
